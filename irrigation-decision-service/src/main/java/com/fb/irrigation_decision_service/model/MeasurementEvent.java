// File: src/main/java/com/fb/irrigation_decision_service/model/MeasurementEvent.java
package com.fb.irrigation_decision_service.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class MeasurementEvent {
    private Long sensorId;
    private Long plotId;
    private String sensorType;
    private Double measuredValue;
    private LocalDateTime measuredAt;

    public MeasurementEvent() {}

    public Long getSensorId() { return sensorId; }
    public void setSensorId(Long sensorId) { this.sensorId = sensorId; }

    public Long getPlotId() { return plotId; }
    public void setPlotId(Long plotId) { this.plotId = plotId; }

    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }

    public Double getMeasuredValue() { return measuredValue; }
    public void setMeasuredValue(Double measuredValue) { this.measuredValue = measuredValue; }

    public LocalDateTime getMeasuredAt() { return measuredAt; }
    public void setMeasuredAt(LocalDateTime measuredAt) { this.measuredAt = measuredAt; }

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
