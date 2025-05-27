package com.fb.irrigation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder

public class PlantationDTO {
    private Long id;

    @NotNull(message = "plotId is mandatory")
    private Long plotId;

    @NotNull(message = "cropId is mandatory")
    private Long cropId;

    @NotNull(message = "PlantingDate is mandatory")
    private LocalDate plantingDate;

    @NotNull (message = "PlantCount is mandatory")
    @Min(value = 1, message = "Minimum plantCount is 1")
    private Long plantCount;
}
