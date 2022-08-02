package com.ajcp.student;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(
		info = @Info(title = "Student service",
				version = "1.0.0",
				description = "Student Microservice"))
public class UStudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UStudentServiceApplication.class, args);
	}

}
