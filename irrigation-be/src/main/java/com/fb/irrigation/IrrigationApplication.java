package com.fb.irrigation;

import com.fb.irrigation.mqtt.MqttProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(MqttProperties.class)
@SpringBootApplication
public class IrrigationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrrigationApplication.class, args);
	}
 //test
}
