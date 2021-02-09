package com.sensedia.poc.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.sensedia.poc.message")
@EnableJpaRepositories
public class Main {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class);
	}
}
