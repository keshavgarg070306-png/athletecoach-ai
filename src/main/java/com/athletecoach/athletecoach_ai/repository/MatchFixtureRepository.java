package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchFixtureRepository extends JpaRepository<MatchFixture, Long> {
    List<MatchFixture> findByPlayerId(Long playerId);
    List<MatchFixture> findByPlayerIdAndMatchDateBetween(
            Long playerId, LocalDate start, LocalDate end);
}