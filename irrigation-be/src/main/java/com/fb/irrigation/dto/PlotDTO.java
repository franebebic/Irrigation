package com.fb.irrigation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlotDTO {
    private Long id;

    @NotNull(message = "Name is mandatory")
    private String name;
    private Double latitude;
    private Double longitude;
}
