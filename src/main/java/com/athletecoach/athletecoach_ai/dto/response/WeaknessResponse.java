package com.athletecoach.athletecoach_ai.dto.response;

import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WeaknessResponse {
    private Long id;
    private WeaknessTag weaknessTag;
    private Severity severity;
    private LocalDateTime notedAt;
}