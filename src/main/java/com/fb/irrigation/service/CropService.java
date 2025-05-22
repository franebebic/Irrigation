package com.fb.irrigation.service;

import com.fb.irrigation.model.Crop;

import java.util.List;
import java.util.Optional;

public interface CropService {
    public Crop save(Crop crop);
    public List<Crop> findAll();
    public Optional<Crop> findById(Long id);
    public void deleteById(Long id);
}
