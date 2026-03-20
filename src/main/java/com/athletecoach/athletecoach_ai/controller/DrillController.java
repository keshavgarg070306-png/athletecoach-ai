package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.request.DrillRequest;
import com.athletecoach.athletecoach_ai.dto.response.DrillResponse;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import com.athletecoach.athletecoach_ai.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drills")
@RequiredArgsConstructor
public class DrillController {

    private final DrillService drillService;

    @PostMapping
    public ResponseEntity<DrillResponse> createDrill(
            @Valid @RequestBody DrillRequest request) {
        return ResponseEntity.ok(drillService.createDrill(request));
    }

    @GetMapping
    public ResponseEntity<List<DrillResponse>> getAllDrills(
            @RequestParam(required = false) SportName sport,
            @RequestParam(required = false) WeaknessTag weakness) {

        if (sport != null) {
            return ResponseEntity.ok(drillService.getDrillsBySport(sport));
        }
        if (weakness != null) {
            return ResponseEntity.ok(drillService.getDrillsByWeakness(weakness));
        }
        return ResponseEntity.ok(drillService.getAllDrills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrillResponse> getDrillById(@PathVariable Long id) {
        return ResponseEntity.ok(drillService.getDrillById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrill(@PathVariable Long id) {
        drillService.deleteDrill(id);
        return ResponseEntity.noContent().build();
    }
}