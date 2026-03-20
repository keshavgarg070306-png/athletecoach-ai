package com.athletecoach.athletecoach_ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AchievementResponse {
    private String code;
    private String name;
    private String description;
    private String iconCode;
    private LocalDateTime earnedAt;
}