package com.fb.irrigation.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementEvent {
    private Long sensorId;
    private Long plotId;
    private String sensorType;
    private Double measuredValue;
    private Instant measuredAt;

    @Override
    public String toString() {
        return "MeasurementEvent{" +
                "sensorId=" + sensorId +
                ", plotId=" + plotId +
                ", sensorType='" + sensorType + '\'' +
                ", measuredValue=" + measuredValue +
                ", measuredAt=" + measuredAt +
                '}';
    }
}
