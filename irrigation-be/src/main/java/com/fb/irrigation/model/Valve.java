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
public class Valve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name="plot_id")
    private Plot plot;

    @OneToMany(mappedBy = "valve", fetch = FetchType.LAZY)
    private List<Activity> activities= new ArrayList<>();
}
