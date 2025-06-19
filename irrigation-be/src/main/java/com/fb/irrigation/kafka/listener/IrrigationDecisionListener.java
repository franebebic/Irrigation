package com.fb.irrigation.kafka.listener;

import com.fb.irrigation.kafka.command.IrrigationCommandEvent;
import com.fb.irrigation.model.ActivityType;
import com.fb.irrigation.service.ValveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class IrrigationDecisionListener {

    private final ValveService valveService;
    @KafkaListener(
            topics = "${kafka.topics.irrigation-command}",
            groupId = "irrigation-decision-consumers",
            properties = {
                    "spring.json.value.default.type=com.fb.irrigation.kafka.command.IrrigationCommandEvent"
            })
    public void handle(IrrigationCommandEvent command) {
        log.info("📥 Received irrigation decision command : {}", command);
        valveService.changeState(command.getValveId(), command.getCommand(), ActivityType.AUTOMATIC);
    }
}