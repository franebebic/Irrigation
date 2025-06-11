package com.fb.irrigation.mqtt;

import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {

    private final MqttClient client;

    public MqttPublisher(MqttProperties properties) throws MqttException {
        client = new MqttClient(properties.getBroker(), properties.getPublisherClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        client.connect(options);
    }

    public void publish(String topic, String payload) {
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(0);
            client.publish(topic, message);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish MQTT message", e);
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            // ignore
        }
    }
}
