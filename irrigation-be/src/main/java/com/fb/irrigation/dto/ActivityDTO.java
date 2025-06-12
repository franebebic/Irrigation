package com.fb.irrigation.dto;

import com.fb.irrigation.model.ActivityStatus;
import com.fb.irrigation.model.ActivityType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityDTO {
    private Long id;

    @NotNull private ActivityType type;

    private Long plotId;
    private String plotName;
    @NotNull private Long plotIdSnapshot;
    @NotNull private String plotNameSnapshot;

    private Long valveId;
    private String valveName;
    @NotNull private Long valveIdSnapshot;
    @NotNull private String valveNameSnapshot;

    @NotNull(message = "Start datetime is mandatory")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull private ActivityStatus status;
}
