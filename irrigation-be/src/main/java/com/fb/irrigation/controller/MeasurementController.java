package com.fb.irrigation.controller;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import com.fb.irrigation.service.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
        List<MeasurementDTO> dtoList = measurementService.findAll();
        return ResponseEntity.ok(dtoList);
    }
}
