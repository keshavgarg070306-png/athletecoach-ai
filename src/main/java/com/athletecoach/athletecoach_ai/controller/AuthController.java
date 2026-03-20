package com.athletecoach.athletecoach_ai.controller;

import com.athletecoach.athletecoach_ai.dto.request.LoginRequest;
import com.athletecoach.athletecoach_ai.dto.request.RegisterRequest;
import com.athletecoach.athletecoach_ai.dto.response.AuthResponse;
import com.athletecoach.athletecoach_ai.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}