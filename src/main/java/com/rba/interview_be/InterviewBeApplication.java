package com.rba.interview_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class InterviewBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewBeApplication.class, args);
	}

}
