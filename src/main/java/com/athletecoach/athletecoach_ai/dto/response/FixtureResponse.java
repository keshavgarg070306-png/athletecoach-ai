package com.athletecoach.athletecoach_ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FixtureResponse {
    private Long id;
    private LocalDate matchDate;
    private String opponent;
    private String venue;
}