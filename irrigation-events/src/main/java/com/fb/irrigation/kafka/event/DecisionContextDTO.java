package com.fb.irrigation.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionContextDTO {
    @NotNull
    private Long plotId;
    private String plotName;

    @NotNull
    private Long valveId;
    private String valveName;
    private boolean valveCurrentlyOpen;

    @NotNull
    private List<MoistureThresholdDTO> moistureThresholds;
}
