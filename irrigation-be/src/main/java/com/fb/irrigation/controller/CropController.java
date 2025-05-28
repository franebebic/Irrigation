package com.fb.irrigation.controller;

import com.fb.irrigation.dto.CropDTO;
import com.fb.irrigation.mapper.CropMapper;
import com.fb.irrigation.model.Crop;
import com.fb.irrigation.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropService cropService;
    private final CropMapper cropMapper;

    @PostMapping
    public ResponseEntity<CropDTO> createCrop(@Valid @RequestBody CropDTO crop) {
        Crop savedCrop = cropService.save(cropMapper.toEntity(crop));
        return ResponseEntity.status(HttpStatus.CREATED).body(cropMapper.toDTO(savedCrop));
    }

    @GetMapping
    public ResponseEntity<List<CropDTO>> getAllCrops() {
        List<CropDTO> crops = cropService.findAll().stream().map(cropMapper::toDTO).toList();
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CropDTO> getCrop(@PathVariable Long id) {
        return cropService.findById(id)
                .map(cropMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        return cropService.findById(id)
                .<ResponseEntity<Void>>map(crop -> {
                    cropService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CropDTO> updateCrop(@PathVariable Long id, @Valid @RequestBody CropDTO updatedCrop) {
        Crop existingCrop = cropService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crop not found"));

        cropMapper.updateEntity(updatedCrop, existingCrop);

        CropDTO saved = cropMapper.toDTO(cropService.save(existingCrop));
        return ResponseEntity.ok(saved);
    }

}
