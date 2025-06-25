package com.fb.irrigation_decision_service.rule.rules;

import com.fb.irrigation_decision_service.rule.FactKey;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "Rain Rule", priority = 2)
public class RainExpectedRule {

    @Condition
    public boolean isRainExpected(@Fact("rainExpectedInNext12h") Boolean rainExpected) {
        return rainExpected;
    }

    @Action
    public void markRainExpected(Facts facts) {
        facts.put(FactKey.SKIP_IRRIGATION_DUE_TO_RAIN.getKey(), true);
    }
}
