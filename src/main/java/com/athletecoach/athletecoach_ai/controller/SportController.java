package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.entity.Sport;
import com.athletecoach.athletecoach_ai.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    @GetMapping
    public ResponseEntity<List<Sport>> getAllSports() {
        return ResponseEntity.ok(sportService.getAllSports());
    }

    @PostMapping
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        return ResponseEntity.ok(sportService.createSport(sport));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sport> getSportById(@PathVariable Long id) {
        return ResponseEntity.ok(sportService.getSportById(id));
    }
}