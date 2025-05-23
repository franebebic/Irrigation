package com.fb.irrigation.controller;

import com.fb.irrigation.model.Crop;
import com.fb.irrigation.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropService cropService;

    @PostMapping
    public ResponseEntity<Crop> createCrop(@Valid @RequestBody Crop crop){
        Crop savedCrop=cropService.save(crop);
        return ResponseEntity.ok(savedCrop);
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops(){
        List<Crop> crops = cropService.findAll();
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCrop(@PathVariable Long id){
        return cropService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id){
        if(cropService.findById(id).isPresent()) {
            cropService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.notFound().build();
    }
}
