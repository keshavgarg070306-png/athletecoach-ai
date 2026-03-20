package com.athletecoach.athletecoach_ai.engine;

import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleEngine {

    private final DrillRepository drillRepository;

    public List<Drill> evaluateWeaknesses(List<WeaknessProfile> weaknesses) {

        List<Drill> assignedDrills = new ArrayList<>();

        for (WeaknessProfile weakness : weaknesses) {

            List<Drill> matchingDrills = drillRepository
                    .findByTargetWeakness(weakness.getWeaknessTag());

            if (matchingDrills.isEmpty()) {
                continue;
            }

            if (weakness.getSeverity() == Severity.HIGH) {
                int count = Math.min(2, matchingDrills.size());
                for (int i = 0; i < count; i++) {
                    if (!assignedDrills.contains(matchingDrills.get(i))) {
                        assignedDrills.add(matchingDrills.get(i));
                    }
                }
            } else if (weakness.getSeverity() == Severity.MEDIUM) {
                if (!assignedDrills.contains(matchingDrills.get(0))) {
                    assignedDrills.add(matchingDrills.get(0));
                }
            }
            // LOW severity — skip
        }

        return assignedDrills;
    }
}