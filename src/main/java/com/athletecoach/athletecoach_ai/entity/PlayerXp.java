package com.athletecoach.athletecoach_ai.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "player_xp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerXp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalXp = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer currentLevel = 1;

    @Column(nullable = false)
    @Builder.Default
    private String levelTitle = "Rookie";

    @Column(nullable = false)
    @Builder.Default
    private Integer currentStreak = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer longestStreak = 0;

    @Column
    private LocalDate lastTrainedDate;
}