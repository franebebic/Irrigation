package com.fb.irrigation.service;

import com.fb.irrigation.model.Plot;

import java.util.List;
import java.util.Optional;

public interface PlotService {
    public Plot save(Plot plot);
    public List<Plot> findAll();
    public Optional<Plot> findById(Long id);
    public void deleteById(Long id);

}
