package com.fb.irrigation.controller;

import com.fb.irrigation.dto.SensorDTO;
import com.fb.irrigation.service.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<SensorDTO> createSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        SensorDTO savedSensorDTO = sensorService.save(sensorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSensorDTO);
    }

    @GetMapping
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        List<SensorDTO> dtoList = sensorService.findAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensor(@PathVariable Long id) {
        return sensorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable Long id, @Valid @RequestBody SensorDTO dto) {
        return ResponseEntity.ok(sensorService.update(id, dto));
    }
}
