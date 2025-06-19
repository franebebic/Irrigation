package com.fb.irrigation.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoistureThresholdDTO {

    @NotNull
    private String cropName;

    @NotNull
    private Integer minMoisture;

    @NotNull
    private Integer maxMoisture;
}
