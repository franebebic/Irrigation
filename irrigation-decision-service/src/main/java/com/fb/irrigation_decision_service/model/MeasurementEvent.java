package com.fb.irrigation_decision_service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeasurementEvent {
    private Long sensorId;
    private Long plotId;
    private String sensorType;
    private Double measuredValue;
    private LocalDateTime measuredAt;

    public MeasurementEvent() {}

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
