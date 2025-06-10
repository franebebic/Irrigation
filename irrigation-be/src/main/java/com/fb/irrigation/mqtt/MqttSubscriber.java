package com.fb.irrigation.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.service.MeasurementService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttSubscriber {

    private static final String BROKER_URL = "tcp://localhost:1883"; // or broker IP
    private static final String CLIENT_ID = "spring-backend";
    private static final String TOPIC = "sensors/#";

    private final ObjectMapper objectMapper;
    private final MeasurementService measurementService;

    @PostConstruct
    public void subscribe() {
        try {
            MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID);
            client.connect();

            client.subscribe(TOPIC, (topic, message) -> {
                String payload = new String(message.getPayload());
                log.info("Received MQTT message. Topic: {}, Content: {}", topic, payload);

                try {
                    MeasurementCreateRequest dto = objectMapper.readValue(payload, MeasurementCreateRequest.class);
                    measurementService.createFromMqtt(dto);
                } catch (Exception e) {
                    log.error("Error while parsing MQTT message", e);
                }
            });

            log.info("MQTT subscription active on '{}'", TOPIC);

        } catch (MqttException e) {
            log.error("Error while connecting to MQTT broker", e);
        }
    }
}
