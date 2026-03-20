package com.athletecoach.athletecoach_ai.entity;

import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.enums.SportName;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportName sport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeaknessTag targetWeakness;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity intensity;

    @Column(length = 1000)
    private String description;
}