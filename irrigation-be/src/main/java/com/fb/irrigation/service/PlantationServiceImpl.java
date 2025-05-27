package com.fb.irrigation.service;

import com.fb.irrigation.dto.PlantationDTO;
import com.fb.irrigation.mapper.PlantationMapper;
import com.fb.irrigation.model.Crop;
import com.fb.irrigation.model.Plantation;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.repository.CropRepository;
import com.fb.irrigation.repository.PlantationRepository;
import com.fb.irrigation.repository.PlotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlantationServiceImpl implements PlantationService {
    private final PlantationRepository plantationRepository;
    private final PlantationMapper plantationMapper;
    private final PlotRepository plotRepository;
    private final CropRepository cropRepository;

    @Override
    public Plantation save(PlantationDTO dto) {

        if (dto.getId() != null && !plantationRepository.existsById(dto.getId()))
            throw new EntityNotFoundException("Plantation with id " + dto.getId() + " not found");


        Plot plot = plotRepository.findById(dto.getPlotId()).orElseThrow(() -> new EntityNotFoundException("Plot not found"));
        Crop crop = cropRepository.findById(dto.getCropId()).orElseThrow(() -> new EntityNotFoundException("Crop not found"));
        Plantation plantation = plantationMapper.toEntity(dto, plot, crop);
        return plantationRepository.save(plantation);
    }

    @Override
    public List<Plantation> findAll() {
        return plantationRepository.findAll();
    }

    @Override
    public Optional<Plantation> findById(Long id) {
        return plantationRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        plantationRepository.deleteById(id);
    }
}
