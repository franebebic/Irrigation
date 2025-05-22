package com.fb.irrigation.controller;

import com.fb.irrigation.model.Crop;
import com.fb.irrigation.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
