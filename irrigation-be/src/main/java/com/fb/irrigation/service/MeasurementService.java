package com.fb.irrigation.service;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    MeasurementDTO save(@Valid MeasurementCreateRequest dto);
    MeasurementDTO save(@Valid MeasurementDTO dto);
    List<MeasurementDTO> findAll();
}
