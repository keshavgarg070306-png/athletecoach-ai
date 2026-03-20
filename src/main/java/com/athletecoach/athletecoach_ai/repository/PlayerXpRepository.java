package com.athletecoach.athletecoach_ai.repository;

import com.athletecoach.athletecoach_ai.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerXpRepository extends JpaRepository<PlayerXp, Long> {

    Optional<PlayerXp> findByPlayerId(Long playerId);

    List<PlayerXp> findTop10ByOrderByTotalXpDesc();

    @Query("SELECT px FROM PlayerXp px JOIN px.player p " +
            "WHERE p.sport = :sport " +
            "ORDER BY px.totalXp DESC")
    List<PlayerXp> findTop10BySportOrderByXpDesc(
            @org.springframework.data.repository.query.Param("sport")
            com.athletecoach.athletecoach_ai.enums.SportName sport);
}