package com.fb.irrigation.controller;

import com.fb.irrigation.dto.MeasurementDTO;
import com.fb.irrigation.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;

    @GetMapping
    public ResponseEntity<Page<MeasurementDTO>> getMeasurements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String sensorName,
            @RequestParam(required = false) String plotName) {
        Page<MeasurementDTO> dtoPage = measurementService.findAll(page, size, sensorName, plotName);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/sensor-options")
    public List<String> getSensorFilterOptions(){
        return measurementService.getSensorFilterOptions();
    }

    @GetMapping("/plot-options")
    public List<String> getPlotFilterOptions(){
        return measurementService.getPlotFilterOptions();
    }
}
