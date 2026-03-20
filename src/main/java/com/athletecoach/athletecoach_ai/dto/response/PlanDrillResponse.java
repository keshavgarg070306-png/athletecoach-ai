package com.athletecoach.athletecoach_ai.dto.response;

import com.athletecoach.athletecoach_ai.enums.TrainingDay;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanDrillResponse {
    private Long id;
    private String drillName;
    private Integer durationMinutes;
    private String intensity;
    private TrainingDay scheduledDay;
    private Boolean completed;
}