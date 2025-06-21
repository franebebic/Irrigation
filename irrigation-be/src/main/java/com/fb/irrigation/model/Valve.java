package com.fb.irrigation.model;

import com.fb.irrigation.kafka.event.ValveStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "plot")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValveStatus status;

    @OneToMany(mappedBy = "valve", fetch = FetchType.LAZY)
    private List<Activity> activities= new ArrayList<>();
}
