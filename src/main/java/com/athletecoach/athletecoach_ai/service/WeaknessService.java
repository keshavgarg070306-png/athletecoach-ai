package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.request.WeaknessRequest;
import com.athletecoach.athletecoach_ai.dto.response.WeaknessResponse;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeaknessService {

    private final WeaknessRepository weaknessRepository;
    private final PlayerRepository playerRepository;

    public WeaknessResponse addWeakness(String email, WeaknessRequest request) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (weaknessRepository.existsByPlayerIdAndWeaknessTag(
                player.getId(), request.getWeaknessTag())) {
            throw new RuntimeException("Weakness already exists");
        }

        WeaknessProfile weakness = WeaknessProfile.builder()
                .player(player)
                .weaknessTag(request.getWeaknessTag())
                .severity(request.getSeverity())
                .build();

        WeaknessProfile saved = weaknessRepository.save(weakness);

        return new WeaknessResponse(
                saved.getId(),
                saved.getWeaknessTag(),
                saved.getSeverity(),
                saved.getNotedAt()
        );
    }

    public List<WeaknessResponse> getMyWeaknesses(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return weaknessRepository
                .findByPlayerIdOrderBySeverityDesc(player.getId())
                .stream()
                .map(w -> new WeaknessResponse(
                        w.getId(),
                        w.getWeaknessTag(),
                        w.getSeverity(),
                        w.getNotedAt()
                ))
                .collect(Collectors.toList());
    }

    public void deleteWeakness(String email, Long weaknessId) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        WeaknessProfile weakness = weaknessRepository.findById(weaknessId)
                .orElseThrow(() -> new RuntimeException("Weakness not found"));

        if (!weakness.getPlayer().getId().equals(player.getId())) {
            throw new RuntimeException("Not authorized");
        }

        weaknessRepository.delete(weakness);
    }

    public WeaknessResponse updateSeverity(String email, Long weaknessId, WeaknessRequest request) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        WeaknessProfile weakness = weaknessRepository.findById(weaknessId)
                .orElseThrow(() -> new RuntimeException("Weakness not found"));

        if (!weakness.getPlayer().getId().equals(player.getId())) {
            throw new RuntimeException("Not authorized");
        }

        weakness.setSeverity(request.getSeverity());
        WeaknessProfile updated = weaknessRepository.save(weakness);

        return new WeaknessResponse(
                updated.getId(),
                updated.getWeaknessTag(),
                updated.getSeverity(),
                updated.getNotedAt()
        );
    }
}