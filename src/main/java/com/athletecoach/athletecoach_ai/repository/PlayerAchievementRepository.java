package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerAchievementRepository
        extends JpaRepository<PlayerAchievement, Long> {
    List<PlayerAchievement> findByPlayerId(Long playerId);
    boolean existsByPlayerIdAndAchievementCode(Long playerId, String code);
}