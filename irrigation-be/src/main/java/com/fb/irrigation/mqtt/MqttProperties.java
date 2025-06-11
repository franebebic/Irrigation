package com.fb.irrigation.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    private String broker;
    private String subscriberClientId;
    private String publisherClientId;
    private String sensorsTopic;
}
