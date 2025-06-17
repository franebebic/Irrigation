package com.fb.irrigation.controller;

import com.fb.irrigation.dto.ValveDTO;
import com.fb.irrigation.model.ActivityType;
import com.fb.irrigation.service.ValveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valves")
@RequiredArgsConstructor
public class ValveController {
    private final ValveService valveService;

    @PostMapping
    public ResponseEntity<ValveDTO> createValve(@Valid @RequestBody ValveDTO dto) {
        ValveDTO savedValveDTO = valveService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedValveDTO);
    }

    @GetMapping
    public ResponseEntity<List<ValveDTO>> getAllValves() {
        List<ValveDTO> dtoList = valveService.findAll();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValveDTO> getValve(@PathVariable Long id) {
        return valveService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValve(@PathVariable Long id) {
        valveService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValveDTO> updateValve(@PathVariable Long id, @Valid @RequestBody ValveDTO dto) {
        return ResponseEntity.ok(valveService.update(id, dto));
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<ValveDTO> toggleValve(@PathVariable Long id) {
        return ResponseEntity.ok(valveService.toggle(id, ActivityType.MANUAL));
    }
}
