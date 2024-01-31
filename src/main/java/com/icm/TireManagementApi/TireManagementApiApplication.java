package com.icm.TireManagementApi;

import com.icm.TireManagementApi.MQTT.MqttSubscriber;
import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Models.RoleModel;
import com.icm.TireManagementApi.Models.UserModel;
import com.icm.TireManagementApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TireManagementApiApplication {

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(TireManagementApiApplication.class, args);

		MqttSubscriber mqttSubscriber = context.getBean(MqttSubscriber.class);
		// Llama a subscribeToTopic en la instancia
		mqttSubscriber.subscribeToTopic("prueba");
	}
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;
/*
	@Bean
	CommandLineRunner init(){
		return args -> {
			CompanyModel empresa = new CompanyModel();
			empresa.setId(1L);

			RoleModel rol = new RoleModel();
			rol.setId(4L);

			UserModel userModel = UserModel.builder()
					.username("sa")
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
