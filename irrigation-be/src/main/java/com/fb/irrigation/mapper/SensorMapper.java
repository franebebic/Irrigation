package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.SensorDTO;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import org.springframework.stereotype.Component;

@Component
public class SensorMapper {
    public Sensor toEntity(SensorDTO dto, Plot plot) {
        return Sensor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .plot(plot)
                .build();
    }

    public SensorDTO toDTO(Sensor sensor) {
        return SensorDTO.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .plotId(sensor.getPlot()!=null? sensor.getPlot().getId():null)
                .plotName(sensor.getPlot()!=null? sensor.getPlot().getName():null)
                .build();
    }
}
