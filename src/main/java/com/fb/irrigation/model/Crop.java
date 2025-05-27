package com.fb.irrigation.model;

import com.fb.irrigation.validation.ValidMoistureRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ValidMoistureRange
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Minimum moisture is mandatory")
    @Min(value = 0, message = "Minimum moisture is 0")
    @Column(nullable = false)
    private Long minMoisture;

    @NotNull(message = "Maximum moisture is mandatory")
    @Min(value = 0, message = "Maximum moisture is 100")
    @Column(nullable = false)
    private Long maxMoisture;

    @OneToMany(mappedBy = "crop", fetch = FetchType.LAZY)
    private List<Plantation> plantations = new ArrayList<>();
}
