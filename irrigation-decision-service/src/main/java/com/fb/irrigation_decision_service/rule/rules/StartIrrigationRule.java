package com.fb.irrigation_decision_service.rule.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "Start irrigation Rule", priority = 3)
public class StartIrrigationRule {

    @Condition
    public boolean shouldIrrigate(@Fact("soilDry") Boolean soilDry,
                                  @Fact("skipIrrigationDueToRain") Boolean skipRain) {
        return Boolean.TRUE.equals(soilDry) && !Boolean.TRUE.equals(skipRain);
    }

    @Action
    public void triggerIrrigation(Facts facts) {
        facts.put("irrigationDecision", true);
    }
}
