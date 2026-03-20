package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.request.WeaknessRequest;
import com.athletecoach.athletecoach_ai.dto.response.WeaknessResponse;
import com.athletecoach.athletecoach_ai.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/weaknesses")
@RequiredArgsConstructor
public class WeaknessController {

    private final WeaknessService weaknessService;

    @PostMapping
    public ResponseEntity<WeaknessResponse> addWeakness(
            @RequestHeader("X-User-Email") String email,
            @Valid @RequestBody WeaknessRequest request) {
        return ResponseEntity.ok(weaknessService.addWeakness(email, request));
    }

    @GetMapping
    public ResponseEntity<List<WeaknessResponse>> getMyWeaknesses(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(weaknessService.getMyWeaknesses(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeakness(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id) {
        weaknessService.deleteWeakness(email, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeaknessResponse> updateSeverity(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id,
            @Valid @RequestBody WeaknessRequest request) {
        return ResponseEntity.ok(weaknessService.updateSeverity(email, id, request));
    }
}
