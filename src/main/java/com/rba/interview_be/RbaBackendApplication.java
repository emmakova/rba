package com.rba.interview_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class RbaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RbaBackendApplication.class, args);
	}

}
