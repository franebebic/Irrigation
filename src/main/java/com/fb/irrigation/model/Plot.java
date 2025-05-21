package com.fb.irrigation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Plot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @OneToOne (mappedBy = "plot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Valve valve;

    @OneToMany (mappedBy = "plot", cascade = CascadeType.ALL)
    private List<Sensor> sensors=new ArrayList<>();

    @OneToMany (mappedBy = "plot", cascade = CascadeType.ALL)
    private List<Plantation> plantations=new ArrayList<>();

    @OneToMany (mappedBy = "plot", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Activity> activities=new ArrayList<>();

    @OneToMany (mappedBy = "plot", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Measurement> measurements=new ArrayList<>();
}