package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.response.LeaderboardEntry;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final PlayerXpRepository playerXpRepository;

    public List<LeaderboardEntry> getGlobalLeaderboard() {
        List<PlayerXp> topPlayers =
                playerXpRepository.findTop10ByOrderByTotalXpDesc();
        return buildLeaderboard(topPlayers);
    }

    public List<LeaderboardEntry> getSportLeaderboard(SportName sport) {
        List<PlayerXp> topPlayers =
                playerXpRepository.findTop10BySportOrderByXpDesc(sport);
        return buildLeaderboard(topPlayers);
    }

    private List<LeaderboardEntry> buildLeaderboard(List<PlayerXp> players) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        int rank = 1;

        for (PlayerXp xp : players) {
            leaderboard.add(new LeaderboardEntry(
                    rank++,
                    xp.getPlayer().getName(),
                    xp.getPlayer().getSport().name(),
                    xp.getTotalXp(),
                    xp.getCurrentLevel(),
                    xp.getLevelTitle(),
                    xp.getCurrentStreak()
            ));
        }

        return leaderboard;
    }
}