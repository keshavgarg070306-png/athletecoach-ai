package com.athletecoach.athletecoach_ai.service;

import com.athletecoach.athletecoach_ai.entity.Sport;
import com.athletecoach.athletecoach_ai.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Sport createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    public Sport getSportById(Long id) {
        return sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found"));
    }
}