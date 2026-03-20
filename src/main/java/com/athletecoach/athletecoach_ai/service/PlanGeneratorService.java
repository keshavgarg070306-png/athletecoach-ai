package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.response.PlanDrillResponse;
import com.athletecoach.athletecoach_ai.dto.response.TrainingPlanResponse;
import com.athletecoach.athletecoach_ai.engine.RuleEngine;
import com.athletecoach.athletecoach_ai.engine.ScheduleBuilder;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.enums.TrainingDay;
import com.athletecoach.athletecoach_ai.repository.*;
import com.athletecoach.athletecoach_ai.security.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanGeneratorService {

    private final PlayerRepository playerRepository;
    private final WeaknessRepository weaknessRepository;
    private final TrainingPlanRepository trainingPlanRepository;
    private final PlanDrillRepository planDrillRepository;
    private final RuleEngine ruleEngine;
    private final ScheduleBuilder scheduleBuilder;
    private final FixtureService fixtureService;
    private final NotificationPublisher notificationPublisher;
    private final XpService xpService;

    public TrainingPlanResponse generatePlan(String email) {

        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        trainingPlanRepository.findByPlayerIdAndActiveTrue(player.getId())
                .ifPresent(plan -> {
                    plan.setActive(false);
                    trainingPlanRepository.save(plan);
                });

        List<WeaknessProfile> weaknesses = weaknessRepository
                .findByPlayerIdOrderBySeverityDesc(player.getId());

        if (weaknesses.isEmpty()) {
            throw new RuntimeException(
                    "Add at least one weakness before generating a plan");
        }

        List<Drill> assignedDrills = ruleEngine.evaluateWeaknesses(weaknesses);

        List<MatchFixture> fixtures = fixtureService
                .getFixturesForWeek(player.getId());

        List<TrainingDay> matchDays = fixtures.stream()
                .map(f -> TrainingDay.valueOf(
                        f.getMatchDate().getDayOfWeek().name()))
                .collect(Collectors.toList());

        Map<TrainingDay, List<Drill>> schedule =
                scheduleBuilder.buildSchedule(assignedDrills, matchDays);

        TrainingPlan plan = TrainingPlan.builder()
                .player(player)
                .weekStartDate(LocalDate.now())
                .totalDrills(assignedDrills.size())
                .active(true)
                .build();

        TrainingPlan savedPlan = trainingPlanRepository.save(plan);

        List<PlanDrill> planDrills = new ArrayList<>();
        for (Map.Entry<TrainingDay, List<Drill>> entry : schedule.entrySet()) {
            for (Drill drill : entry.getValue()) {
                PlanDrill planDrill = PlanDrill.builder()
                        .trainingPlan(savedPlan)
                        .drill(drill)
                        .scheduledDay(entry.getKey())
                        .completed(false)
                        .build();
                planDrills.add(planDrill);
            }
        }

        planDrillRepository.saveAll(planDrills);

        notificationPublisher.sendPlanReadyNotification(
                email,
                savedPlan.getId(),
                assignedDrills.size()
        );

        return convertToResponse(savedPlan, planDrills);
    }

    public TrainingPlanResponse getCurrentPlan(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        TrainingPlan plan = trainingPlanRepository
                .findByPlayerIdAndActiveTrue(player.getId())
                .orElseThrow(() -> new RuntimeException(
                        "No active plan found. Generate one first!"));

        List<PlanDrill> planDrills =
                planDrillRepository.findByTrainingPlanId(plan.getId());

        return convertToResponse(plan, planDrills);
    }

    public PlanDrillResponse completeDrill(String email, Long planDrillId) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        PlanDrill planDrill = planDrillRepository.findById(planDrillId)
                .orElseThrow(() -> new RuntimeException("Drill not found"));

        planDrill.setCompleted(true);
        PlanDrill updated = planDrillRepository.save(planDrill);

        notificationPublisher.sendDrillCompletedNotification(
                email,
                updated.getDrill().getName()
        );

        xpService.awardDrillXp(email);

        return new PlanDrillResponse(
                updated.getId(),
                updated.getDrill().getName(),
                updated.getDrill().getDurationMinutes(),
                updated.getDrill().getIntensity().name(),
                updated.getScheduledDay(),
                updated.getCompleted()
        );
    }

    private TrainingPlanResponse convertToResponse(
            TrainingPlan plan, List<PlanDrill> planDrills) {

        Map<String, List<PlanDrillResponse>> scheduleMap = new LinkedHashMap<>();

        for (TrainingDay day : TrainingDay.values()) {
            List<PlanDrillResponse> dayDrills = planDrills.stream()
                    .filter(pd -> pd.getScheduledDay() == day)
                    .map(pd -> new PlanDrillResponse(
                            pd.getId(),
                            pd.getDrill().getName(),
                            pd.getDrill().getDurationMinutes(),
                            pd.getDrill().getIntensity().name(),
                            pd.getScheduledDay(),
                            pd.getCompleted()
                    ))
                    .collect(Collectors.toList());

            if (!dayDrills.isEmpty()) {
                scheduleMap.put(day.name(), dayDrills);
            }
        }

        return new TrainingPlanResponse(
                plan.getId(),
                plan.getWeekStartDate(),
                plan.getTotalDrills(),
                plan.getActive(),
                plan.getGeneratedAt(),
                scheduleMap
        );
    }
}