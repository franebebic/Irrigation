package com.fb.irrigation.controller;

import com.fb.irrigation.dto.PlotDTO;
import com.fb.irrigation.mapper.PlotMapper;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.service.PlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/plots")
@RequiredArgsConstructor
@Tag(name = "Plots", description = "API for managing plots")
public class PlotController {
    private final PlotService plotService;
    private final PlotMapper plotMapper;

    @Operation(summary = "Create a new plot")
    @PostMapping
    public ResponseEntity<PlotDTO> createPlot(@Valid @RequestBody PlotDTO plot) {
        Plot savedPlot = plotService.save(plotMapper.toEntity(plot));
        return ResponseEntity.status(HttpStatus.CREATED).body(plotMapper.toDTO(savedPlot));
    }

    @Operation(summary = "Get all plots")
    @GetMapping
    public ResponseEntity<List<PlotDTO>> getAllPlots() {
        List<PlotDTO> plots = plotService.findAll()
                .stream().map(plotMapper::toDTO).toList();
        return ResponseEntity.ok(plots);
    }

    @Operation(summary = "Get a plot by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PlotDTO> getPlot(@PathVariable Long id) {
        return plotService.findById(id)
                .map(plotMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a plot by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlot(@PathVariable Long id) {
        return plotService.findById(id)
                .<ResponseEntity<Void>>map(plot -> {
                    plotService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing plot")
    @PutMapping("/{id}")
    public ResponseEntity<PlotDTO> updatePlot(@PathVariable Long id, @Valid @RequestBody PlotDTO updatedPlot) {
        Plot plot = plotService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plot not found"));

        plotMapper.updateEntity(updatedPlot, plot);

        Plot saved = plotService.save(plot);
        return ResponseEntity.ok(plotMapper.toDTO(saved));
    }

}
