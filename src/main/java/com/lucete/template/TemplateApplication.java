package com.lucete.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import java.util.Collections;

@OpenAPIDefinition(servers = {@Server(url = "https://lucetemusical.com")})
@SpringBootApplication
public class TemplateApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TemplateApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.address", "0.0.0.0"));
		app.run(args);
	}

}
