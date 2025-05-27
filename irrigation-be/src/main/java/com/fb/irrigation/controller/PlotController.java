package com.fb.irrigation.controller;

import com.fb.irrigation.model.Plot;
import com.fb.irrigation.service.PlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plots")
@RequiredArgsConstructor
public class PlotController {
    private final PlotService plotService;

    @PostMapping
    public ResponseEntity<Plot> createPlot(@Valid @RequestBody Plot plot) {
        Plot savedPlot = plotService.save(plot);
        return ResponseEntity.ok(savedPlot);
    }

    @GetMapping
    public ResponseEntity<List<Plot>> getAllPlots() {
        List<Plot> crops = plotService.findAll();
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plot> getCrop(@PathVariable Long id) {
        return plotService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlot(@PathVariable Long id) {
        if (plotService.findById(id).isPresent()) {
            plotService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plot> updateCrop(@PathVariable Long id, @Valid @RequestBody Plot updatedPlot) {
        Optional<Plot> existingPlotOpt = plotService.findById(id);
        if (existingPlotOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Plot existingPlot = existingPlotOpt.get();
        existingPlot.setName(updatedPlot.getName());

        Plot saved = plotService.save(existingPlot);
        return ResponseEntity.ok(saved);
    }
}
