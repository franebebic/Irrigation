package com.fb.irrigation.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.service.MeasurementService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttSubscriber {
    private final ObjectMapper objectMapper;
    private final MeasurementService measurementService;
    private final MqttProperties properties;
    private MqttClient client;

    @PostConstruct
    public void subscribe() {
        try {
            client = new MqttClient(properties.getBroker(), properties.getClientId());
            client.connect();

            client.subscribe(properties.getSensorsTopic(), (topic, message) -> {
                String payload = new String(message.getPayload());
                log.info("Received MQTT message. Topic: {}, Content: {}", topic, payload);

                try {
                    MeasurementCreateRequest dto = objectMapper.readValue(payload, MeasurementCreateRequest.class);
                    measurementService.createFromMqtt(dto);
                } catch (Exception e) {
                    log.error("Error while parsing MQTT message", e);
                }
            });

            log.info("MQTT subscription active on '{}'", properties.getSensorsTopic());

        } catch (MqttException e) {
            log.error("Error while connecting to MQTT broker", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                client.close();
            }
        } catch (MqttException e) {
            log.error("Error during MQTT client shutdown", e);
        }
    }
}
