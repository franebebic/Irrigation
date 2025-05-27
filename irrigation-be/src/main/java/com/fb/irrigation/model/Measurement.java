package com.fb.irrigation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @NotNull
    @Column(nullable = false)
    private Long sensorIdSnapshot;

    @NotNull
    @Column(nullable = false)
    private String sensorNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementType type;

    @NotNull(message = "Measured date is mandatory")
    @Column(nullable = false)
    private LocalDateTime measuredAt;

    @NotNull(message = "Measured value is mandatory")
    @Column(nullable = false)
    private Double measuredValue;
}
