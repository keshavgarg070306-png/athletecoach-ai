package com.athletecoach.athletecoach_ai.entity;

import com.athletecoach.athletecoach_ai.enums.PlayerRole;
import com.athletecoach.athletecoach_ai.enums.SportName;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "player")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportName sport;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
