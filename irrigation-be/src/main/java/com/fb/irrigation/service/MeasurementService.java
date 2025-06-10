package com.fb.irrigation.service;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MeasurementService {
    @Deprecated
    MeasurementDTO save(@Valid MeasurementCreateRequest dto);
    void createFromMqtt(@Valid MeasurementCreateRequest dto);
    MeasurementDTO save(@Valid MeasurementDTO dto);
    List<MeasurementDTO> findAll();

    Page<MeasurementDTO> findAll(int page, int size, String sensorName, String plotName);
    List<String> getSensorFilterOptions();
    List<String> getPlotFilterOptions();
}
