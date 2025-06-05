package com.fb.irrigation.controller;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import com.fb.irrigation.service.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;

    @PostMapping("/from-esp")
    public ResponseEntity<MeasurementDTO> createMeasurement(@Valid @RequestBody MeasurementCreateRequest dto) {

        MeasurementDTO savedMeasurementDTO = measurementService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeasurementDTO);
    }

    @PostMapping
    public ResponseEntity<MeasurementDTO> createMeasurement(@Valid @RequestBody MeasurementDTO dto) {
        MeasurementDTO savedMeasurementDTO = measurementService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeasurementDTO);
    }

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
