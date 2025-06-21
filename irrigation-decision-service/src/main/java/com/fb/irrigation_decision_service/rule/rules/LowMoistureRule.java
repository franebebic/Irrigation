package com.fb.irrigation_decision_service.rule.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "Low Moisture Rule", priority = 1)
public class LowMoistureRule {
    @Condition
    public boolean isSoilDry(@Fact("moisture") Double moisture,
                             @Fact("moistureMinThreshold") Double min) {
        return moisture < min;
    }

    @Action
    public void setFlag(Facts facts) {
        facts.put("shouldStartIrrigation", true);
    }
}
