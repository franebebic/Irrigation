package com.fb.irrigation.service;

import com.fb.irrigation.model.Plot;
import com.fb.irrigation.repository.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlotServiceImpl implements PlotService{
    private final PlotRepository plotRepository;

    @Override
    public Plot save(Plot plot) {
        return plotRepository.save(plot);
    }

    @Override
    public List<Plot> findAll() {
        return plotRepository.findAll();
    }

    @Override
    public Optional<Plot> findById(Long id) {
        return plotRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        plotRepository.deleteById(id);
    }
}
