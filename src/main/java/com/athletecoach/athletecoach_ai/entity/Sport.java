package com.athletecoach.athletecoach_ai.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    public String description;

    @Column
    public String iconCode;

}
