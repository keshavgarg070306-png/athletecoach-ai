package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.dto.request.DrillRequest;
import com.athletecoach.athletecoach_ai.dto.response.DrillResponse;
import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import com.athletecoach.athletecoach_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrillService {

    private final DrillRepository drillRepository;

    public DrillResponse createDrill(DrillRequest request) {
        Drill drill = Drill.builder()
                .name(request.getName())
                .sport(request.getSport())
                .targetWeakness(request.getTargetWeakness())
                .durationMinutes(request.getDurationMinutes())
                .intensity(request.getIntensity())
                .description(request.getDescription())
                .build();

        Drill saved = drillRepository.save(drill);
        return convertToResponse(saved);
    }

    public List<DrillResponse> getAllDrills() {
        return drillRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<DrillResponse> getDrillsBySport(SportName sport) {
        return drillRepository.findBySport(sport)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<DrillResponse> getDrillsByWeakness(WeaknessTag tag) {
        return drillRepository.findByTargetWeakness(tag)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public DrillResponse getDrillById(Long id) {
        Drill drill = drillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drill not found"));
        return convertToResponse(drill);
    }

    public void deleteDrill(Long id) {
        drillRepository.deleteById(id);
    }

    private DrillResponse convertToResponse(Drill drill) {
        return new DrillResponse(
                drill.getId(),
                drill.getName(),
                drill.getSport(),
                drill.getTargetWeakness(),
                drill.getDurationMinutes(),
                drill.getIntensity(),
                drill.getDescription()
        );
    }
}