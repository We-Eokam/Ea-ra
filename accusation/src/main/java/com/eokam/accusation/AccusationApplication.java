package com.eokam.accusation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
public class AccusationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccusationApplication.class, args);
	}

}
