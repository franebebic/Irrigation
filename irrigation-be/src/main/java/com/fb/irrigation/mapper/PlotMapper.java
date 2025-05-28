package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.PlotDTO;
import com.fb.irrigation.model.Plot;
import org.springframework.stereotype.Component;

@Component
public class PlotMapper {

    public void updateEntity(PlotDTO updatedPlot, Plot plot) {
        plot.setName(updatedPlot.getName());
    }

    public Plot toEntity(PlotDTO dto) {
        return Plot.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public PlotDTO toDTO(Plot plot) {
        return PlotDTO.builder()
                .id(plot.getId())
                .name(plot.getName())
                .build();
    }
}
