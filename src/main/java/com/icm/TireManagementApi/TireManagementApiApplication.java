package com.icm.TireManagementApi;

import com.icm.TireManagementApi.MQTT.MqttSubscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TireManagementApiApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TireManagementApiApplication.class, args);

		MqttSubscriber mqttSubscriber = context.getBean(MqttSubscriber.class);
		// Llama a subscribeToTopic en la instancia
		mqttSubscriber.subscribeToTopic("prueba");
	}
}
