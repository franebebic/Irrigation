package com.fb.irrigation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @Column(nullable = false)
    private Integer minMoisture;

    @NotNull(message = "Maximum moisture is mandatory")
    @Column(nullable = false)
    private Integer maxMoisture;

    @OneToMany(mappedBy = "crop", fetch = FetchType.LAZY)
    private List<Plantation> plantations = new ArrayList<>();
}
