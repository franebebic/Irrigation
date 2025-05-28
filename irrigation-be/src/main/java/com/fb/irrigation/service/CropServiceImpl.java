package com.fb.irrigation.service;

import com.fb.irrigation.model.Crop;
import com.fb.irrigation.repository.CropRepository;
import com.fb.irrigation.repository.PlantationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CropServiceImpl implements CropService{
    private final CropRepository cropRepository;
    private final PlantationRepository plantationRepository;
    @Override
    public Crop save(Crop crop) {
        return cropRepository.save(crop);
    }

    @Override
    public List<Crop> findAll() {
        return cropRepository.findAll();
    }

    @Override
    public Optional<Crop> findById(Long id) {
        return cropRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if(plantationRepository.existsByCrop_Id(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete crop — it is used by plantations.");
        }
        cropRepository.deleteById(id);
    }
}
