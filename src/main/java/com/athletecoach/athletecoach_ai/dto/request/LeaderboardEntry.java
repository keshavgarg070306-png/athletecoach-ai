package com.athletecoach.athletecoach_ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaderboardEntry {
    private Integer rank;
    private String playerName;
    private String sport;
    private Integer totalXp;
    private Integer currentLevel;
    private String levelTitle;
    private Integer currentStreak;
}