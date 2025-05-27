package com.fb.irrigation.controller;

import com.fb.irrigation.dto.PlantationDTO;
import com.fb.irrigation.mapper.PlantationMapper;
import com.fb.irrigation.model.Plantation;
import com.fb.irrigation.service.PlantationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plantations")
@RequiredArgsConstructor
public class PlantationController {
    private final PlantationService plantationService;
    private final PlantationMapper plantationMapper;
    @PostMapping
    public ResponseEntity<PlantationDTO> createPlantation(@Valid @RequestBody PlantationDTO plantationDTO){
        Plantation savedPlantation=plantationService.save(plantationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(plantationMapper.toDTO(savedPlantation));
    }

    @GetMapping
    public ResponseEntity<List<PlantationDTO>> getAllPlantations(){
        List<PlantationDTO> dtoList = plantationService.findAll()
                .stream()
                .map(plantationMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantationDTO> getPlantation(@PathVariable Long id){
        return plantationService.findById(id)
                .map(plantationMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantation(@PathVariable Long id){
        if(plantationService.findById(id).isPresent()) {
            plantationService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantationDTO> updatePlantation(@PathVariable Long id, @Valid @RequestBody PlantationDTO dto) {
        if(dto.getId()!=null && ! dto.getId().equals(id))
            return ResponseEntity.badRequest().build();
        dto.setId(id);

        Plantation updated = plantationService.save(dto);

        PlantationDTO responseDTO = plantationMapper.toDTO(updated);

        return ResponseEntity.ok(responseDTO);
    }
}
