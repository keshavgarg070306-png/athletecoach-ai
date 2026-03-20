package com.athletecoach.athletecoach_ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private LocalDate weekStartDate;

    @Column(nullable = false)
    private Integer totalDrills;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime generatedAt;

    @OneToMany(mappedBy = "trainingPlan",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<PlanDrill> planDrills = new ArrayList<>();
}