package com.athletecoach.athletecoach_ai.entity;

import com.athletecoach.athletecoach_ai.enums.TrainingDay;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_drill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDrill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlan trainingPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drill_id", nullable = false)
    private Drill drill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingDay scheduledDay;

    @Column(nullable = false)
    private Boolean completed;
}