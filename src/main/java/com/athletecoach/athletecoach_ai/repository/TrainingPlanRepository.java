package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
    List<TrainingPlan> findByPlayerIdOrderByGeneratedAtDesc(Long playerId);
    Optional<TrainingPlan> findByPlayerIdAndActiveTrue(Long playerId);
}