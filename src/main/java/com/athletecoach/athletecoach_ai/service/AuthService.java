package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.request.LoginRequest;
import com.athletecoach.athletecoach_ai.dto.request.RegisterRequest;
import com.athletecoach.athletecoach_ai.dto.response.AuthResponse;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.repository.*;
import com.athletecoach.athletecoach_ai.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        if (playerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Player player = Player.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .sport(request.getSport())
                .role(request.getRole())
                .build();

        playerRepository.save(player);

        String token = jwtUtil.generateToken(player.getEmail());

        return new AuthResponse(
                token,
                player.getName(),
                player.getEmail(),
                player.getSport().name(),
                player.getRole().name()
        );
    }

    public AuthResponse login(LoginRequest request) {

        Player player = playerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), player.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(player.getEmail());

        return new AuthResponse(
                token,
                player.getName(),
                player.getEmail(),
                player.getSport().name(),
                player.getRole().name()
        );
    }
}