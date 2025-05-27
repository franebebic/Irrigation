package com.fb.irrigation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="plot_id", nullable = false)
    private Plot plot;

    @ManyToOne
    @JoinColumn(name="crop_id", nullable = false)
    private Crop crop;

    @NotNull (message = "PlantingDate is mandatory")
    @Column(nullable = false)
    private LocalDate plantingDate;

    @NotNull (message = "PlantCount is mandatory")
    @Column(nullable = false)
    @Min(value = 1, message = "Minimum plantCount is 1")
    private Long plantCount;
}
