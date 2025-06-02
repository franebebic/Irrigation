package com.fb.irrigation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDTO {
    private Long id;
    private String name;
    private Long plotId;
    private String plotName;
}
