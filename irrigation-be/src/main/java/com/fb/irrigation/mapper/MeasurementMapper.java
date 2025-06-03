package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.MeasurementDTO;
import com.fb.irrigation.model.Measurement;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {

    public Measurement toEntity(MeasurementDTO dto, Sensor sensor, Plot plot) {
        return Measurement.builder()
                .id(dto.getId())
                .sensor(sensor)
                .sensorIdSnapshot(sensor.getId())
                .sensorNameSnapshot(sensor.getName())
                .plot(plot)
                .plotIdSnapshot(plot.getId())
                .plotNameSnapshot(plot.getName())
                .measuredAt(dto.getMeasuredAt())
                .measuredValue(dto.getMeasuredValue())
                .type(dto.getType())
                .build();
    }

    public MeasurementDTO toDTO(Measurement measurement) {
        Sensor sensor = measurement.getSensor();
        Plot plot = measurement.getPlot();

        return MeasurementDTO.builder()
                .id(measurement.getId())
                .sensorId(sensor != null ? sensor.getId() : null)
                .sensorName(sensor != null ? sensor.getName() : null)
                .sensorIdSnapshot(measurement.getSensorIdSnapshot())
                .sensorNameSnapshot(measurement.getSensorNameSnapshot())
                .plotId(plot != null ? plot.getId() : null)
                .plotName(plot != null ? plot.getName() : null)
                .plotIdSnapshot(measurement.getPlotIdSnapshot())
                .plotNameSnapshot(measurement.getPlotNameSnapshot())
                .measuredAt(measurement.getMeasuredAt())
                .measuredValue(measurement.getMeasuredValue())
                .type(measurement.getType())
                .build();
    }
}
