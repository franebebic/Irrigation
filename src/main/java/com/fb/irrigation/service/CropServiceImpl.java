package com.fb.irrigation.service;

import com.fb.irrigation.model.Crop;
import com.fb.irrigation.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CropServiceImpl implements CropService{
    private final CropRepository cropRepository;

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
        cropRepository.deleteById(id);
    }
}
