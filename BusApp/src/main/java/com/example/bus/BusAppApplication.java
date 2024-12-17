package com.example.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.example", "com.example.security"})
@EnableJpaRepositories
public class BusAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusAppApplication.class, args);
	}

}
