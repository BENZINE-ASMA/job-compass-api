package com.dauphine.jobComapss;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="Job Portal backend microservice",
				description="Job Portal backend microservice exposing endpoints",
				contact = @Contact(
						name = "Asma & Leaticia",
						email = "asma.ben-zine@dauphine.eu, Leaticia.Aidoune@dauphine.eu"
				),
				version= "1.0.0"
		)
)
public class JobCompassApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobCompassApiApplication.class, args);
	}

}
