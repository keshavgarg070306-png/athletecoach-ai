package com.athletecoach.athletecoach_ai.dto.request;

import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DrillRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Sport is required")
    private SportName sport;

    @NotNull(message = "Target weakness is required")
    private WeaknessTag targetWeakness;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    @NotNull(message = "Intensity is required")
    private Severity intensity;

    private String description;
}