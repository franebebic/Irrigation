package com.fb.irrigation_decision_service.service;

import com.fb.irrigation.kafka.command.IrrigationCommandType;
import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MeasurementEvent;
import com.fb.irrigation_decision_service.dto.WeatherForecast;
import com.fb.irrigation_decision_service.model.MoistureThresholdRange;
import com.fb.irrigation_decision_service.rule.RuleEngineService;
import com.fb.irrigation_decision_service.service.logic.MoistureThresholdCalculator;
import com.fb.irrigation_decision_service.service.weather.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IrrigationDecisionService {
    private final RuleEngineService ruleEngineService;
    private final WeatherService weatherService;

    public IrrigationCommandType evaluateCommand(MeasurementEvent measurement, DecisionContextDTO context) {
        double measuredValue = measurement.getMeasuredValue();
        MoistureThresholdRange range = MoistureThresholdCalculator.calculate(context.getMoistureThresholds());

        WeatherForecast forecast = weatherService.getHourlyPrecipitation(context.getLatitude(), context.getLongitude());
        double rainNext12h = forecast.getTotalPrecipitationNextHours(12);

        boolean rainExpected = rainNext12h >= 1.0;

        return ruleEngineService.evaluate(measuredValue, range, rainExpected, context.getValveStatus());
    }
}
