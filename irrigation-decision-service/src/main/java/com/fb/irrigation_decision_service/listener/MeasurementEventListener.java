package com.fb.irrigation_decision_service.listener;

import com.fb.irrigation_decision_service.model.MeasurementEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MeasurementEventListener {

    @KafkaListener(topics = "measurement.created", groupId = "decision-group")
    public void handle(MeasurementEvent event) {
        System.out.println("📥 Received: " + event);
    }
}