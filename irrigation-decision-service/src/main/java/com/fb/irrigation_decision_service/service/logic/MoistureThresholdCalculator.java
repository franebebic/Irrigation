package com.fb.irrigation_decision_service.service.logic;


import com.fb.irrigation.kafka.event.MoistureThresholdDTO;
import com.fb.irrigation_decision_service.model.MoistureThresholdRange;

import java.util.List;

public class MoistureThresholdCalculator {

    public static MoistureThresholdRange calculate(List<MoistureThresholdDTO> thresholds) {
        if (thresholds == null || thresholds.isEmpty()) {
            throw new IllegalArgumentException("No moisture thresholds provided");
        }

        double min = thresholds.stream()
                .mapToDouble(MoistureThresholdDTO::getMinMoisture)
                .max()
                .orElseThrow();

        double max = thresholds.stream()
                .mapToDouble(MoistureThresholdDTO::getMaxMoisture)
                .min()
                .orElseThrow();

        if (min > max) {
            throw new IllegalStateException("Incompatible moisture ranges (min > max)");
        }

        return new MoistureThresholdRange(min, max);
    }
}
