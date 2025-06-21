package com.fb.irrigation_decision_service.service;

import com.fb.irrigation.kafka.command.IrrigationCommandType;
import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MeasurementEvent;
import com.fb.irrigation_decision_service.model.MoistureThresholdRange;
import com.fb.irrigation_decision_service.rule.RuleEngineService;
import com.fb.irrigation_decision_service.service.logic.MoistureThresholdCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IrrigationDecisionService {
    private final RuleEngineService ruleEngineService;

    public IrrigationCommandType evaluateCommand(MeasurementEvent measurement, DecisionContextDTO context) {
        double measuredValue = measurement.getMeasuredValue();
        MoistureThresholdRange range = MoistureThresholdCalculator.calculate(context.getMoistureThresholds());
        return ruleEngineService.evaluate(measuredValue, range, false, context.getValveStatus());
    }
}
