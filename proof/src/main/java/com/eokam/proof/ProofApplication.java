package com.eokam.proof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProofApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProofApplication.class, args);
	}

}
