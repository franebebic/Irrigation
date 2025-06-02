package com.fb.irrigation.service;

import com.fb.irrigation.dto.SensorDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface SensorService {
    SensorDTO save(@Valid SensorDTO sensorDTO);
    SensorDTO update(Long id, @Valid SensorDTO sensorDTO);
    List<SensorDTO> findAll();
    Optional<SensorDTO> findById(Long id);
    void deleteById(Long id);
}
