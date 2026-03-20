package com.athletecoach.athletecoach_ai.engine;

import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.enums.TrainingDay;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleBuilder {

    private static final int MAX_DRILLS_PER_DAY = 3;

    public Map<TrainingDay, List<Drill>> buildSchedule(
            List<Drill> drills,
            List<TrainingDay> matchDays) {

        Map<TrainingDay, List<Drill>> schedule = new HashMap<>();

        List<TrainingDay> trainingDays = new ArrayList<>();
        for (TrainingDay day : TrainingDay.values()) {
            if (!matchDays.contains(day)) {
                trainingDays.add(day);
                schedule.put(day, new ArrayList<>());
            }
        }

        if (trainingDays.isEmpty()) {
            return schedule;
        }

        int dayIndex = 0;
        for (Drill drill : drills) {

            boolean assigned = false;
            int attempts = 0;

            while (!assigned && attempts < trainingDays.size()) {
                TrainingDay day = trainingDays.get(dayIndex % trainingDays.size());
                List<Drill> dayDrills = schedule.get(day);

                if (dayDrills.size() < MAX_DRILLS_PER_DAY) {
                    dayDrills.add(drill);
                    assigned = true;
                }

                dayIndex++;
                attempts++;
            }
        }

        return schedule;
    }
}