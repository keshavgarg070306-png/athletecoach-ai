package com.athletecoach.athletecoach_ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TrainingPlanResponse {
    private Long id;
    private LocalDate weekStartDate;
    private Integer totalDrills;
    private Boolean active;
    private LocalDateTime generatedAt;
    private Map<String, List<PlanDrillResponse>> schedule;
}