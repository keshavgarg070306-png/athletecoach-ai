package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.response.PlanDrillResponse;
import com.athletecoach.athletecoach_ai.dto.response.TrainingPlanResponse;
import com.athletecoach.athletecoach_ai.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanGeneratorService planGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<TrainingPlanResponse> generatePlan(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(planGeneratorService.generatePlan(email));
    }

    @GetMapping("/current")
    public ResponseEntity<TrainingPlanResponse> getCurrentPlan(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(planGeneratorService.getCurrentPlan(email));
    }

    @PostMapping("/drills/{id}/complete")
    public ResponseEntity<PlanDrillResponse> completeDrill(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id) {
        return ResponseEntity.ok(planGeneratorService.completeDrill(email, id));
    }
}