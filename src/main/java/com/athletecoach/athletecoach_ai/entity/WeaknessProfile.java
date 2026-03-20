package com.athletecoach.athletecoach_ai.entity;

import com.athletecoach.athletecoach_ai.enums.Severity;
import com.athletecoach.athletecoach_ai.enums.WeaknessTag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "weakness_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeaknessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeaknessTag weaknessTag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @CreationTimestamp
    private LocalDateTime notedAt;
}