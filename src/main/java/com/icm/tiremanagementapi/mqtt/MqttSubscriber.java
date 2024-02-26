package com.icm.tiremanagementapi.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icm.tiremanagementapi.models.TireSensorModel;
import com.icm.tiremanagementapi.services.TireSensorService;
import com.icm.tiremanagementapi.services.TireService;
import com.icm.tiremanagementapi.models.TireModel;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component responsible for subscribing to MQTT topics and processing received messages.
 * Utilizes the Paho MQTT client library for MQTT communication.
 */
@Component
public class MqttSubscriber {

    @Autowired
    private TireSensorService tireSensorService;
    @Autowired
    private IMqttClient mqttClient;

    /**
     * Subscribes to a specified MQTT topic and defines how messages received on that topic are processed.
     *
     * @param topic The MQTT topic to subscribe to.
     */
    public void subscribeToTopic(String topic) {
        try {
            /// Subscription to the given MQTT topic
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // Convert message payload to string
                    String payload = new String(message.getPayload());
                    System.out.println("Mensaje MQTT recibido en el tema " + topic + ": " + payload);

                    // Attempt to deserialize the message payload into a TireModel object
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        TireSensorModel tireModel = objectMapper.readValue(payload, TireSensorModel.class);
                        // Extract necessary properties from the TireModel object
                        Long idvehicle = tireModel.getVehicleModel().getId();
                        Long idtire = tireModel.getId();
                        // Update tire properties based on the received message
                        tireSensorService.updateProperties(tireModel.getTemperature(), tireModel.getPressure(), tireModel.getBatteryLevel().intValue(), idvehicle, idtire);
                    } catch (JsonProcessingException e) {
                        // Handle JSON parsing errors
                        System.err.println("Error al deserializar el JSON: " + e.getMessage());
                    }
                }
            });
        } catch (MqttException e) {
            // Handle exceptions related to MQTT subscription
            e.printStackTrace();
        }
    }

    /**
     * This section is for testing purposes to verify the MQTT server connection and message reception.
     * If you encounter issues with connecting to the server or receiving messages,
     * uncomment this section and comment out the main subscribeToTopic method to diagnose connection issues.
     * Observe the console for any success or error messages to ensure the connection is established correctly.
     * This testing method can easily be toggled by commenting/uncommenting as needed.
     *
     * @param topic The MQTT topic for testing subscription.
     *
     * To send a basic message to the MQTT server for example, use the following command:
     * mosquitto_pub -t topic -m "This is a test message"
     *
     */
    /*
    public void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic, (topic1, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Received MQTT message on topic \"" + topic1 + "\": " + payload);
            });
        } catch (MqttException e) {
            e.printStackTrace();
            System.err.println("Failed to subscribe to MQTT topic due to an exception: " + e.getMessage());
        }
    }
     */
}