package com.fb.irrigation_decision_service.rule.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "Stop Irrigation Rule", description = "Turn off irrigation if soil is too wet", priority = 4)
public class StopIrrigationRule {
    @Condition
    public boolean isTooWet(@Fact("moisture") Double moisture,
                             @Fact("moistureMaxThreshold") Double max) {
        return moisture > max;
    }

    @Action
    public void setFlag(Facts facts) {
        facts.put("shouldStopIrrigation", true);
    }
}
