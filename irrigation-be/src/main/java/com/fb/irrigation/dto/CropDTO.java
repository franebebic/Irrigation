package com.fb.irrigation.dto;

import com.fb.irrigation.validation.ValidMoistureRange;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@ValidMoistureRange
@Data
@Builder
public class CropDTO {
    private Long id;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Minimum moisture is mandatory")
    @Min(value = 0, message = "Moisture must be ≥ 0")
    @Max(value = 100, message = "Moisture must be ≤ 100")
    private Integer minMoisture;

    @NotNull(message = "Maximum moisture is mandatory")
    @Min(value = 0, message = "Moisture must be ≥ 0")
    @Max(value = 100, message = "Moisture must be ≤ 100")
    private Integer maxMoisture;
}
