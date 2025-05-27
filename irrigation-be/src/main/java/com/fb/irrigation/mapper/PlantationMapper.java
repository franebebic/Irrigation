package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.PlantationDTO;
import com.fb.irrigation.model.Crop;
import com.fb.irrigation.model.Plantation;
import com.fb.irrigation.model.Plot;
import org.springframework.stereotype.Component;

@Component
public class PlantationMapper {
    public Plantation toEntity(PlantationDTO dto, Plot plot, Crop crop) {
        return Plantation.builder()
                .id(dto.getId())
                .plot(plot)
                .crop(crop)
                .plantingDate(dto.getPlantingDate())
                .plantCount(dto.getPlantCount())
                .build();
    }

    public PlantationDTO toDTO(Plantation plantation) {
        return PlantationDTO.builder()
                .id(plantation.getId())
                .cropId(plantation.getCrop().getId())
                .plotId(plantation.getPlot().getId())
                .plantingDate(plantation.getPlantingDate())
                .plantCount(plantation.getPlantCount())
                .build();
    }
}
