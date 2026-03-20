package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.response.AchievementResponse;
import com.athletecoach.athletecoach_ai.dto.response.XpResponse;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.repository.*;
import com.athletecoach.athletecoach_ai.security.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class XpService {

    private static final int XP_PER_DRILL = 10;
    private static final int XP_FULL_DAY_BONUS = 25;
    private static final int XP_STREAK_7_BONUS = 50;

    private static final int[] LEVEL_THRESHOLDS =
            {0, 200, 500, 1000, 2000, 4000, 7500, 12000};
    private static final String[] LEVEL_TITLES = {
            "Rookie", "Trainee", "Amateur", "Club Player",
            "District Player", "State Player", "National Prospect", "Elite Athlete"
    };

    private final PlayerXpRepository playerXpRepository;
    private final PlayerRepository playerRepository;
    private final AchievementRepository achievementRepository;
    private final PlayerAchievementRepository playerAchievementRepository;
    private final NotificationPublisher notificationPublisher;

    public void awardDrillXp(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        PlayerXp xp = playerXpRepository.findByPlayerId(player.getId())
                .orElseGet(() -> createNewXpRecord(player));

        xp.setTotalXp(xp.getTotalXp() + XP_PER_DRILL);

        updateStreak(xp);
        updateLevel(xp, email);
        checkAchievements(xp, player, email);

        playerXpRepository.save(xp);
    }

    public XpResponse getMyXp(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        PlayerXp xp = playerXpRepository.findByPlayerId(player.getId())
                .orElseGet(() -> createNewXpRecord(player));

        int nextLevelXp = getNextLevelXp(xp.getCurrentLevel());

        return new XpResponse(
                xp.getTotalXp(),
                xp.getCurrentLevel(),
                xp.getLevelTitle(),
                xp.getCurrentStreak(),
                xp.getLongestStreak(),
                xp.getLastTrainedDate(),
                nextLevelXp - xp.getTotalXp()
        );
    }

    public List<AchievementResponse> getMyAchievements(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return playerAchievementRepository.findByPlayerId(player.getId())
                .stream()
                .map(pa -> new AchievementResponse(
                        pa.getAchievement().getCode(),
                        pa.getAchievement().getName(),
                        pa.getAchievement().getDescription(),
                        pa.getAchievement().getIconCode(),
                        pa.getEarnedAt()
                ))
                .collect(Collectors.toList());
    }

    private void updateStreak(PlayerXp xp) {
        LocalDate today = LocalDate.now();
        LocalDate lastTrained = xp.getLastTrainedDate();

        if (lastTrained == null) {
            xp.setCurrentStreak(1);
        } else if (lastTrained.equals(today.minusDays(1))) {
            xp.setCurrentStreak(xp.getCurrentStreak() + 1);
            if (xp.getCurrentStreak() == 7) {
                xp.setTotalXp(xp.getTotalXp() + XP_STREAK_7_BONUS);
            }
        } else if (!lastTrained.equals(today)) {
            xp.setCurrentStreak(1);
        }

        if (xp.getCurrentStreak() > xp.getLongestStreak()) {
            xp.setLongestStreak(xp.getCurrentStreak());
        }

        xp.setLastTrainedDate(today);
    }

    private void updateLevel(PlayerXp xp, String email) {
        int oldLevel = xp.getCurrentLevel();

        for (int i = LEVEL_THRESHOLDS.length - 1; i >= 0; i--) {
            if (xp.getTotalXp() >= LEVEL_THRESHOLDS[i]) {
                xp.setCurrentLevel(i + 1);
                xp.setLevelTitle(LEVEL_TITLES[i]);
                break;
            }
        }

        if (xp.getCurrentLevel() > oldLevel) {
            notificationPublisher.sendLevelUpNotification(
                    email, xp.getLevelTitle());
        }
    }

    private void checkAchievements(PlayerXp xp, Player player, String email) {
        checkAndAward(player, email, "FIRST_DRILL",
                "First Step", "Complete your first drill", "👟");

        if (xp.getCurrentStreak() >= 7) {
            checkAndAward(player, email, "STREAK_7",
                    "On Fire", "Train 7 days in a row", "🔥");
        }

        if (xp.getCurrentStreak() >= 30) {
            checkAndAward(player, email, "STREAK_30",
                    "Unstoppable", "Train 30 days in a row", "⚡");
        }

        if (xp.getTotalXp() >= 1000) {
            checkAndAward(player, email, "XP_1000",
                    "Dedicated", "Earn 1000 XP", "🏅");
        }
    }

    private void checkAndAward(Player player, String email,
                               String code, String name, String description, String icon) {

        if (!playerAchievementRepository
                .existsByPlayerIdAndAchievementCode(player.getId(), code)) {

            Achievement achievement = achievementRepository.findByCode(code)
                    .orElseGet(() -> {
                        Achievement newAchievement = Achievement.builder()
                                .code(code)
                                .name(name)
                                .description(description)
                                .iconCode(icon)
                                .build();
                        return achievementRepository.save(newAchievement);
                    });

            PlayerAchievement playerAchievement = PlayerAchievement.builder()
                    .player(player)
                    .achievement(achievement)
                    .build();

            playerAchievementRepository.save(playerAchievement);

            notificationPublisher.sendLevelUpNotification(
                    email, "Badge earned: " + name + " " + icon);
        }
    }

    private PlayerXp createNewXpRecord(Player player) {
        PlayerXp xp = PlayerXp.builder()
                .player(player)
                .totalXp(0)
                .currentLevel(1)
                .levelTitle("Rookie")
                .currentStreak(0)
                .longestStreak(0)
                .build();
        return playerXpRepository.save(xp);
    }

    private int getNextLevelXp(int currentLevel) {
        if (currentLevel >= LEVEL_THRESHOLDS.length) {
            return LEVEL_THRESHOLDS[LEVEL_THRESHOLDS.length - 1];
        }
        return LEVEL_THRESHOLDS[currentLevel];
    }
}