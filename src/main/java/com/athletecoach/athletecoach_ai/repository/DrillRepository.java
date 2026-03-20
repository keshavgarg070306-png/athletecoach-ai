package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.*;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DrillRepository extends JpaRepository<Drill, Long> {
    List<Drill> findBySport(SportName sport);
    List<Drill> findByTargetWeakness(WeaknessTag tag);
    List<Drill> findBySportAndTargetWeakness(SportName sport, WeaknessTag tag);
}