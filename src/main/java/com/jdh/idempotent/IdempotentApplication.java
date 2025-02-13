package com.jdh.idempotent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IdempotentApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdempotentApplication.class, args);
	}

}
