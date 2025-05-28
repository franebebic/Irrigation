package com.fb.irrigation.mapper;

import com.fb.irrigation.dto.CropDTO;
import com.fb.irrigation.model.Crop;
import org.springframework.stereotype.Component;

@Component
public class CropMapper {
    public void updateEntity(CropDTO updatedCrop, Crop existingCrop) {
        existingCrop.setName(updatedCrop.getName());
        existingCrop.setMinMoisture(updatedCrop.getMinMoisture());
        existingCrop.setMaxMoisture(updatedCrop.getMaxMoisture());
    }

    public Crop toEntity(CropDTO dto) {
        return Crop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .minMoisture(dto.getMinMoisture())
                .maxMoisture(dto.getMaxMoisture())
                .build();
    }

    public CropDTO toDTO(Crop crop) {
        return CropDTO.builder()
                .id(crop.getId())
                .name(crop.getName())
                .minMoisture(crop.getMinMoisture())
                .maxMoisture(crop.getMaxMoisture())
                .build();
    }
}
