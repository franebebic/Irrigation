package com.fb.irrigation_decision_service.service;

import com.fb.irrigation.kafka.command.IrrigationCommandEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, IrrigationCommandEvent> kafkaTemplate;

    @Value("${kafka.topics.irrigation-command}")
    private String irrigationCommandTopic;

    public void sendIrrigationCommand(IrrigationCommandEvent command) {
        log.info("🚀 Sending irrigation command: {}", command);
        kafkaTemplate.send(irrigationCommandTopic, command);
    }
}
