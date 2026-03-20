package com.athletecoach.athletecoach_ai.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class FixtureRequest {

    @NotNull(message = "Match date is required")
    private LocalDate matchDate;

    private String opponent;
    private String venue;
}