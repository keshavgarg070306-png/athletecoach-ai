package com.athletecoach.athletecoach_ai.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "match_fixture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchFixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private LocalDate matchDate;

    @Column
    private String opponent;

    @Column
    private String venue;
}
