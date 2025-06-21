package com.fb.irrigation_decision_service.rule.rules;

import com.fb.irrigation.kafka.command.IrrigationCommandType;
import com.fb.irrigation_decision_service.rule.FactKey;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "Final Decision Rule", description = "Converts internal flags into a final irrigation command", priority = 99)
public class FinalDecisionRule {

    @Condition
    public boolean hasDecision(
            @Fact("shouldStartIrrigation") Boolean start,
            @Fact("shouldStopIrrigation") Boolean stop) {
        return Boolean.TRUE.equals(start) || Boolean.TRUE.equals(stop);
    }

    @Action
    public void applyFinalDecision(Facts facts) {
        if (Boolean.TRUE.equals(facts.get(FactKey.SHOULD_START_IRRIGATION.toString()))) {
            facts.put(FactKey.IRRIGATION_COMMAND.toString(), IrrigationCommandType.OPEN);
        } else if (Boolean.TRUE.equals(facts.get(FactKey.SHOULD_STOP_IRRIGATION.toString()))) {
            facts.put(FactKey.IRRIGATION_COMMAND.toString(), IrrigationCommandType.CLOSE);
        }
    }
}
