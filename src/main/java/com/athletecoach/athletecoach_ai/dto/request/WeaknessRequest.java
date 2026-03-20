package com.athletecoach.athletecoach_ai.dto.request;

import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WeaknessRequest {

    @NotNull(message = "Weakness tag is required")
    private WeaknessTag weaknessTag;

    @NotNull(message = "Severity is required")
    private Severity severity;
}