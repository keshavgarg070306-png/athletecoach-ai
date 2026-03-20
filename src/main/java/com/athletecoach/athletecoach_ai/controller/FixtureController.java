package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.request.FixtureRequest;
import com.athletecoach.athletecoach_ai.dto.response.FixtureResponse;
import com.athletecoach.athletecoach_ai.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fixtures")
@RequiredArgsConstructor
public class FixtureController {

    private final FixtureService fixtureService;

    @PostMapping
    public ResponseEntity<FixtureResponse> addFixture(
            @RequestHeader("X-User-Email") String email,
            @Valid @RequestBody FixtureRequest request) {
        return ResponseEntity.ok(fixtureService.addFixture(email, request));
    }

    @GetMapping
    public ResponseEntity<List<FixtureResponse>> getMyFixtures(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(fixtureService.getMyFixtures(email));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<FixtureResponse>> getUpcomingFixtures(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(fixtureService.getUpcomingFixtures(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFixture(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Long id) {
        fixtureService.deleteFixture(email, id);
        return ResponseEntity.noContent().build();
    }
}