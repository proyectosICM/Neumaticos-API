package com.icm.tiremanagementapi; 	

import com.icm.tiremanagementapi.mqtt.MqttSubscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Main class for the Tire Management API application, incorporating MQTT functionality.
 * Serves as the entry point for the Spring Boot application, setting up the application context.
 * It initializes necessary components, including MQTT message handling,
 * by subscribing to topics and processing incoming messages.
 * This setup demonstrates the integration of MQTT communication within a Spring Boot application,
 * facilitating real-time data exchange with IoT devices or other MQTT-enabled systems.
 */

@SpringBootApplication
public class TireManagementApiApplication {

	public static void main(String[] args) {
		// Initialize Spring Boot application and get the application context
		ApplicationContext context = SpringApplication.run(TireManagementApiApplication.class, args);

		// Retrieve the MqttSubscriber bean from the application context
		// This bean is responsible for subscribing to MQTT topics and handling incoming messages
		MqttSubscriber mqttSubscriber = context.getBean(MqttSubscriber.class);

		// Subscribe to a test topic to start receiving messages.
		// This is a placeholder and should be replaced with actual topic names relevant to your application.
		mqttSubscriber.subscribeToTopic("prueba");
	}
/*
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {
			CompanyModel empresa = new CompanyModel();
			empresa.setId(1L);
git

			RoleModel rol = new RoleModel();
			rol.setId(1L);

			UserModel userModel = UserModel.builder()
					.username("sup")
					.password(passwordEncoder.encode("1234"))
					.name("Eduardo")
					.lastname("Antahurco")
					.email("eluis3@gmail.com")
					.role(rol)
					.company(empresa)
					.build();
			userRepository.save(userModel);
		};
	}
 */
}
