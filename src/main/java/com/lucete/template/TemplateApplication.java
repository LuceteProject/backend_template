package com.lucete.template;

import com.lucete.template.info.utils.KeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;

@SpringBootApplication
public class TemplateApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TemplateApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.address", "0.0.0.0"));
		app.run(args);
	}

}
