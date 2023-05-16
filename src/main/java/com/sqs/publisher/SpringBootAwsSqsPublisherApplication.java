package com.sqs.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootAwsSqsPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAwsSqsPublisherApplication.class, args);
	}

}
