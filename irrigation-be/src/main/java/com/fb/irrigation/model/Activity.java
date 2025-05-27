package com.fb.irrigation.model;

import com.fb.irrigation.validation.ValidActivityTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ValidActivityTime
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @ManyToOne
    @JoinColumn(name = "plot_id")
    private Plot plot;

    @NotNull
    @Column(nullable = false)
    private Long plotIdSnapshot;

    @NotNull
    @Column(nullable = false)
    private String plotNameSnapshot;

    @ManyToOne
    @JoinColumn(name = "valve_id")
    private Valve valve;

    @NotNull
    @Column(nullable = false)
    private Long valveIdSnapshot;

    @NotNull
    @Column(nullable = false)
    private String valveNameSnapshot;

    @NotNull(message = "Start datetime is mandatory")
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column()
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityStatus status;
}
