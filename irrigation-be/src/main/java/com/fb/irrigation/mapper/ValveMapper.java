package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.ValveDTO;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Valve;
import org.springframework.stereotype.Component;

@Component
public class ValveMapper {
    public Valve toEntity(ValveDTO dto, Plot plot) {
        return Valve.builder()
                .id(dto.getId())
                .name(dto.getName())
                .status(dto.getStatus())
                .plot(plot)
                .build();
    }

    public ValveDTO toDTO(Valve valve) {
        Plot plot = valve.getPlot();
        return ValveDTO.builder()
                .id(valve.getId())
                .name(valve.getName())
                .status(valve.getStatus())
                .plotId(plot != null ? plot.getId() : null)
                .plotName(plot != null ? plot.getName() : null)
                .build();
    }
}

