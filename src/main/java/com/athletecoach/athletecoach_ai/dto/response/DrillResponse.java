package com.athletecoach.athletecoach_ai.dto.response;

import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrillResponse {
    private Long id;
    private String name;
    private SportName sport;
    private WeaknessTag targetWeakness;
    private Integer durationMinutes;
    private Severity intensity;
    private String description;
}