package com.icm.TireManagementApi.MQTT;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {
    /*
    @Autowired
    private BateriaService bateriaService;
    @Autowired
    private BateriaRepositoriy bateriaRepositoriy;
*/
    @Autowired
    private IMqttClient mqttClient;


        public void subscribeToTopic(String topic) {
            try {
                mqttClient.subscribe(topic, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        String payload = new String(message.getPayload());
                        System.out.println("Mensaje MQTT recibido en el tema " + topic + ": " + payload);
                        // Procesa el mensaje según tus necesidades
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }


    /*
    public void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    System.out.println("Mensaje MQTT recibido en el tema " + topic + ": " + payload);

                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        BateriasModels bateriasModels = objectMapper.readValue(payload, BateriasModels.class);

                        // Guarda el objeto en la base de datos utilizando el servicio
                        bateriaService.EstadosBateria(bateriasModels);
                    } catch (JsonProcessingException e) {
                        System.err.println("Error al deserializar el JSON: " + e.getMessage());
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

     */
}