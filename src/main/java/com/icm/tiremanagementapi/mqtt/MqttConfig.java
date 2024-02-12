package com.icm.tiremanagementapi.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

/**
 * Configuration class for setting up MQTT communication within the Tire Management System.
 * Defines beans for establishing MQTT client connections and sending messages to an MQTT broker
 * based on application properties.
 */
@Configuration
public class MqttConfig {
    @Value("${mqtt.serverUri}")
    private String serverUri;

    @Value("${mqtt.topic}")
    private String topic;

    /**
     * Creates an MQTT client with connection options configured for the application.
     * Utilizes the MQTT server URI specified in the application properties.
     *
     * @return An instance of MqttClient connected to the MQTT broker.
     * @throws MqttException if there is an issue establishing the connection with the MQTT broker.
     */
    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{serverUri});

        MqttClient client = new MqttClient(serverUri, MqttClient.generateClientId(), new MemoryPersistence());
        try {
            client.connect(options);
        } catch (MqttException e) {
            throw e;
        }
        return client;
    }

    /**
     * Configures a client factory for creating MQTT clients with predefined connection options.
     * This factory supports the creation of clients for both inbound and outbound MQTT communication.
     *
     * @return A configured instance of MqttPahoClientFactory.
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{serverUri});
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * Defines an outbound message handler for publishing messages to the MQTT broker.
     * Messages are sent asynchronously to the default topic specified in the application properties.
     *
     * @return A configured instance of MqttPahoMessageHandler for outbound messaging.
     */
    @Bean
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttClient.generateClientId(), mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topic);
        return messageHandler;
    }
}