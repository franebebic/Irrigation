package com.fb.irrigation_decision_service.service;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MeasurementEvent;
import com.fb.irrigation.kafka.event.MoistureThresholdDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IrrigationDecisionService {

    public boolean shouldIrrigate(MeasurementEvent measurement, DecisionContextDTO context) {
        double measuredValue = measurement.getMeasuredValue();

        // Nađi najstroži prag (najviši minMoisture)
        return context.getMoistureThresholds().stream()
                .mapToDouble(MoistureThresholdDTO::getMinMoisture)
                .max()
                .stream()
                .anyMatch(threshold -> measuredValue < threshold);
    }
}
