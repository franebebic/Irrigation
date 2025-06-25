package com.fb.irrigation_decision_service.rule.rules;

import com.fb.irrigation_decision_service.rule.FactKey;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "Start irrigation Rule", priority = 3)
public class StartIrrigationRule {

    @Condition
    public boolean shouldIrrigate(@Fact("moistureLow") Boolean moistureLow,
                                  @Fact("skipIrrigationDueToRain") Boolean skipRain) {
        return Boolean.TRUE.equals(moistureLow) && !Boolean.TRUE.equals(skipRain);
    }

    @Action
    public void triggerIrrigation(Facts facts) {
        facts.put(FactKey.SHOULD_START_IRRIGATION.getKey(), true);
    }
}
