package com.fb.irrigation.service;

import com.fb.irrigation.model.Plot;
import com.fb.irrigation.repository.PlantationRepository;
import com.fb.irrigation.repository.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlotServiceImpl implements PlotService{
    private final PlotRepository plotRepository;
    private final PlantationRepository plantationRepository;

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

        if(plantationRepository.existsByPlot_Id(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete plot — it is used by plantations.");
        }

        plotRepository.deleteById(id);
    }
}
