package com.fb.irrigation.service;

import com.fb.irrigation.dto.PlantationDTO;
import com.fb.irrigation.model.Plantation;

import java.util.List;
import java.util.Optional;

public interface PlantationService {
    public Plantation save(PlantationDTO plantationDTO);
    public List<Plantation> findAll();
    public Optional<Plantation> findById(Long id);
    public void deleteById(Long id);
}
