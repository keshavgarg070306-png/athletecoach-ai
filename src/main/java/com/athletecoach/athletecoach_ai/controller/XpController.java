package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.response.AchievementResponse;
import com.athletecoach.athletecoach_ai.dto.response.XpResponse;
import com.athletecoach.athletecoach_ai.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/xp")
@RequiredArgsConstructor
public class XpController {

    private final XpService xpService;

    @GetMapping
    public ResponseEntity<XpResponse> getMyXp(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(xpService.getMyXp(email));
    }

    @GetMapping("/achievements")
    public ResponseEntity<List<AchievementResponse>> getMyAchievements(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(xpService.getMyAchievements(email));
    }
}