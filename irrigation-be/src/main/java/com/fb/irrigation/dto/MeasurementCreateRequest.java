package com.fb.irrigation.dto;

import com.fb.irrigation.model.MeasurementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementCreateRequest {
    @NotBlank(message = "Sensor name is mandatory")
    private String sensorName;
    @NotNull(message = "MeasurementType is mandatory")
    private MeasurementType type;
    @NotNull(message = "Measured value is mandatory")
    private Double measuredValue;
}
