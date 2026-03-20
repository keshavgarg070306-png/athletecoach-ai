package com.athletecoach.athletecoach_ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class XpResponse {
    private Integer totalXp;
    private Integer currentLevel;
    private String levelTitle;
    private Integer currentStreak;
    private Integer longestStreak;
    private LocalDate lastTrainedDate;
    private Integer xpToNextLevel;
}