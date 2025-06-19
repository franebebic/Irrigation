package com.fb.irrigation.context;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.service.ContextService;
import com.fb.irrigation.kafka.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ContextInitializer {

    private final ContextService contextService;
    private final KafkaProducerService kafkaProducerService;

    @EventListener(ApplicationReadyEvent.class)
    public void sendInitialDecisionContexts() {
        List<DecisionContextDTO> allContexts = contextService.buildAllContexts();
        log.info("Sending {} decision contexts to Kafka", allContexts.size());
        allContexts.forEach(kafkaProducerService::publishDecisionContext);
    }
}
