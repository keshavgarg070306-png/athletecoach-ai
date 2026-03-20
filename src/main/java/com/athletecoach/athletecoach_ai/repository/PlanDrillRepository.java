package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlanDrillRepository extends JpaRepository<PlanDrill, Long> {
    List<PlanDrill> findByTrainingPlanId(Long planId);
    List<PlanDrill> findByTrainingPlanIdAndCompleted(Long planId, Boolean completed);
}