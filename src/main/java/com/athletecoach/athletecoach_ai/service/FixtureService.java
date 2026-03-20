package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.request.FixtureRequest;
import com.athletecoach.athletecoach_ai.dto.response.FixtureResponse;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FixtureService {

    private final MatchFixtureRepository fixtureRepository;
    private final PlayerRepository playerRepository;

    public FixtureResponse addFixture(String email, FixtureRequest request) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        MatchFixture fixture = MatchFixture.builder()
                .player(player)
                .matchDate(request.getMatchDate())
                .opponent(request.getOpponent())
                .venue(request.getVenue())
                .build();

        MatchFixture saved = fixtureRepository.save(fixture);
        return convertToResponse(saved);
    }

    public List<FixtureResponse> getMyFixtures(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return fixtureRepository.findByPlayerId(player.getId())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<FixtureResponse> getUpcomingFixtures(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        return fixtureRepository.findByPlayerIdAndMatchDateBetween(
                        player.getId(), today, nextWeek)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public void deleteFixture(String email, Long fixtureId) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        MatchFixture fixture = fixtureRepository.findById(fixtureId)
                .orElseThrow(() -> new RuntimeException("Fixture not found"));

        if (!fixture.getPlayer().getId().equals(player.getId())) {
            throw new RuntimeException("Not authorized");
        }

        fixtureRepository.delete(fixture);
    }

    public List<MatchFixture> getFixturesForWeek(Long playerId) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return fixtureRepository.findByPlayerIdAndMatchDateBetween(
                playerId, today, nextWeek);
    }

    private FixtureResponse convertToResponse(MatchFixture fixture) {
        return new FixtureResponse(
                fixture.getId(),
                fixture.getMatchDate(),
                fixture.getOpponent(),
                fixture.getVenue()
        );
    }
}