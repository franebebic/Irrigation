package com.fb.irrigation.kafka.service;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MeasurementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, MeasurementEvent> kafkaTemplateMeasurementEvent;
    private final KafkaTemplate<String, DecisionContextDTO> kafkaTemplateDecisionContext;

    @Value("${kafka.topics.measurement}")
    private String measurementTopic;

    @Value("${kafka.topics.decision-context}")
    private String decisionContextTopic;

    public void publishMeasurementEvent(MeasurementEvent event) {
        kafkaTemplateMeasurementEvent.send(measurementTopic, event);
    }

    public void publishDecisionContext(DecisionContextDTO decisionContextDTO) {
        kafkaTemplateDecisionContext.send(decisionContextTopic, decisionContextDTO);
        log.debug("Publishing DecisionContext for plot: {}", decisionContextDTO.getPlotName());
    }
}
