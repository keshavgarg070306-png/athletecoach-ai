package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.WeaknessProfile;
import com.athletecoach.athletecoach_ai.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WeaknessRepository extends JpaRepository<WeaknessProfile, Long> {
    List<WeaknessProfile> findByPlayerId(Long playerId);
    List<WeaknessProfile> findByPlayerIdOrderBySeverityDesc(Long playerId);
    boolean existsByPlayerIdAndWeaknessTag(Long playerId,
                                           com.athletecoach.athletecoach_ai.enums.WeaknessTag tag);
}