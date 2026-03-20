package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.response.LeaderboardEntry;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/global")
    public ResponseEntity<List<LeaderboardEntry>> getGlobalLeaderboard() {
        return ResponseEntity.ok(
                leaderboardService.getGlobalLeaderboard());
    }

    @GetMapping("/sport/{sport}")
    public ResponseEntity<List<LeaderboardEntry>> getSportLeaderboard(
            @PathVariable SportName sport) {
        return ResponseEntity.ok(
                leaderboardService.getSportLeaderboard(sport));
    }
}