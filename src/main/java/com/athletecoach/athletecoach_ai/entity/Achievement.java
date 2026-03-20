package com.athletecoach.athletecoach_ai.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "achievement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String iconCode;

    @Column
    private String sport;
}