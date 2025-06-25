package com.fb.irrigation.kafka.mapper;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MoistureThresholdDTO;
import com.fb.irrigation.model.Plantation;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Valve;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@NoArgsConstructor
public class ContextMapper {

    public DecisionContextDTO toDecisionContextDTO(Plot plot) {
        Valve valve = plot.getValve();
        return DecisionContextDTO.builder()
                .plotId(plot.getId())
                .plotName(plot.getName())
                .valveId(valve!=null?valve.getId():null)
                .valveName(valve!=null?valve.getName():null)
                .valveStatus(valve!=null?valve.getStatus():null)
                .moistureThresholds(getMoistureThresholdDTOS(plot))
                .build();
    }

    private List<MoistureThresholdDTO> getMoistureThresholdDTOS(Plot plot) {
        List<MoistureThresholdDTO> thresholds = new ArrayList<>();
        for (Plantation plantation : plot.getPlantations()) {
            MoistureThresholdDTO moistureThresholdDTO = MoistureThresholdDTO.builder()
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
