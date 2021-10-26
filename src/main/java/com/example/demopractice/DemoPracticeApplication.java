package com.example.demopractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class DemoPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoPracticeApplication.class, args);
	}

}
