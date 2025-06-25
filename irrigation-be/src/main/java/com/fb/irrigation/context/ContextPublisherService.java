package com.fb.irrigation.context;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.mapper.ContextMapper;
import com.fb.irrigation.kafka.service.KafkaProducerService;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.repository.PlotRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ContextPublisherService {

    private final KafkaProducerService kafkaProducerService;
    private final PlotRepository plotRepository;
    private final ContextMapper contextMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void sendInitialDecisionContexts() {
        List<Plot> plots = plotRepository.findAllWithPlantationsAndValve();
        List<DecisionContextDTO> allContexts = plots.stream().map(contextMapper::toDecisionContextDTO).toList();
        log.info("Sending {} decision contexts to Kafka", allContexts.size());
        allContexts.forEach(kafkaProducerService::publishDecisionContext);
    }

    public void sendDecisionContext(Plot plot) {
        DecisionContextDTO decisionContext=contextMapper.toDecisionContextDTO(plot);
        log.info("📤 Sending decision context for plot {} to Kafka", decisionContext.getPlotName());
        kafkaProducerService.publishDecisionContext(decisionContext);
    }
}
