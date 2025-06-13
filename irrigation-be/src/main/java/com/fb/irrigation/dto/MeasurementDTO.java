package com.fb.irrigation.dto;

import com.fb.irrigation.model.MeasurementType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MeasurementDTO {
    private Long id;

    private Long plotId;
    private String plotName;
    @NotNull private Long plotIdSnapshot;
    @NotNull private String plotNameSnapshot;

    private Long sensorId;
    private String sensorName;
    @NotNull private Long sensorIdSnapshot;
    @NotNull private String sensorNameSnapshot;

    @NotNull(message = "MeasurementType is mandatory")
    private MeasurementType type;

    @NotNull(message = "Measured date is mandatory")
    private Instant measuredAt;

    @NotNull(message = "Measured value is mandatory")
    private Double measuredValue;
}
