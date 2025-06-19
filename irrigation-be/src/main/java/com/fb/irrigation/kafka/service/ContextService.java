package com.fb.irrigation.kafka.service;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MoistureThresholdDTO;
import com.fb.irrigation.model.Plantation;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.ValveStatus;
import com.fb.irrigation.repository.PlotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ContextService {
    private final PlotRepository plotRepository;

    public List<DecisionContextDTO> buildAllContexts() {

        List<DecisionContextDTO> contexts = new ArrayList<>();
        List<Plot> plots = plotRepository.findAllWithPlantationsAndValve();

        for (Plot plot : plots) {
            if(plot.getValve()==null) {
                continue;
            }

            DecisionContextDTO dto = DecisionContextDTO.builder()
                    .plotId(plot.getId())
                    .plotName(plot.getName())
                    .valveId(plot.getValve().getId())
                    .valveName(plot.getValve().getName())
                    .valveCurrentlyOpen(plot.getValve().getStatus() == ValveStatus.OPEN)
                    .moistureThresholds(getMoistureThresholdDTOS(plot))
                    .build();

            contexts.add(dto);
        }
        return contexts;
    }

    private static List<MoistureThresholdDTO> getMoistureThresholdDTOS(Plot plot) {
        List<MoistureThresholdDTO> thresholds = new ArrayList<>();
        for(Plantation plantation: plot.getPlantations()){
            MoistureThresholdDTO moistureThresholdDTO=MoistureThresholdDTO.builder()
                    .cropName(plantation.getCrop().getName())
                    .minMoisture(plantation.getCrop().getMinMoisture())
                    .maxMoisture(plantation.getCrop().getMaxMoisture())
                    .build();
            thresholds.add(moistureThresholdDTO);
        }

        thresholds.sort(Comparator.comparing(MoistureThresholdDTO::getCropName));
        return thresholds;
    }
}
