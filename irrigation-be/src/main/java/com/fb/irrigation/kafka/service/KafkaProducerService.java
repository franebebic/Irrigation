package com.fb.irrigation.kafka.service;

import com.fb.irrigation.kafka.event.MeasurementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, MeasurementEvent> kafkaTemplate;

    public void publishMeasurementEvent(MeasurementEvent event) {
        kafkaTemplate.send("measurement.created", event);
    }
}
