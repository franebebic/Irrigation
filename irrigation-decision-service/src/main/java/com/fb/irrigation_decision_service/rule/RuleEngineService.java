package com.fb.irrigation_decision_service.rule;

import com.fb.irrigation.kafka.command.IrrigationCommandType;
import com.fb.irrigation.kafka.event.ValveStatus;
import com.fb.irrigation_decision_service.model.MoistureThresholdRange;
import com.fb.irrigation_decision_service.rule.rules.*;
import lombok.RequiredArgsConstructor;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RuleEngineService {

    private final RulesEngine rulesEngine = new DefaultRulesEngine();

    public IrrigationCommandType evaluate(double moisture,
                                          MoistureThresholdRange thresholdRange,
                                          boolean rainExpected,
                                          ValveStatus valveStatus) {

        Facts facts = new Facts();
        facts.put(FactKey.MOISTURE.toString(), moisture);
        facts.put(FactKey.MOISTURE_MIN_THRESHOLD.toString(), thresholdRange.min());
        facts.put(FactKey.MOISTURE_MAX_THRESHOLD.toString(), thresholdRange.max());
        facts.put(FactKey.RAIN_EXPECTED_IN_NEXT_12H.toString(), rainExpected);
        facts.put(FactKey.VALVE_CURRENT_STATUS.toString(), valveStatus);

        Rules rules = new Rules();
        rules.register(new LowMoistureRule());
        rules.register(new RainExpectedRule());
        rules.register(new StartIrrigationRule());
        rules.register(new StopIrrigationRule());
        rules.register(new FinalDecisionRule());

        rulesEngine.fire(rules, facts);

        IrrigationCommandType decision = facts.get(FactKey.IRRIGATION_COMMAND.toString());

        if (decision == IrrigationCommandType.OPEN && valveStatus != ValveStatus.OPEN) {
            return IrrigationCommandType.OPEN;
        } else if (decision == IrrigationCommandType.CLOSE && valveStatus != ValveStatus.CLOSED) {
            return IrrigationCommandType.CLOSE;
        } else {
            return IrrigationCommandType.NONE;
        }
    }
}