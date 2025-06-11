package com.fb.irrigation.service;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MeasurementService {
    void createFromMqtt(@Valid MeasurementCreateRequest dto);
    List<MeasurementDTO> findAll();

    Page<MeasurementDTO> findAll(int page, int size, String sensorName, String plotName);
    List<String> getSensorFilterOptions();
    List<String> getPlotFilterOptions();
}
