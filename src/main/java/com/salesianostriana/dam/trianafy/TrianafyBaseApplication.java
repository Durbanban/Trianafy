package com.salesianostriana.dam.trianafy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				description = "Una API para gestionar la app Trianafy",
				version = "1.0",
				contact = @Contact(
						email = "carlosdurban91@gmail.com", name = "Carlos"
				),
				license = @License(name = "Pedazo de API"),
				title = "API con mucho ritmo"
		)
)
public class TrianafyBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrianafyBaseApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner init(ApplicationContext ctx) {
		return args -> {
			Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
		};
	}*/

}
