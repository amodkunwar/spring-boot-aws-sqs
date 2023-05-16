package com.sqs.publisher.config;

import org.springframework.context.annotation.Configuration;

import com.sqs.publisher.service.MessageQueueService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

	private final MessageQueueService messageQueueService;

	@PostConstruct
	public void initializeMessageQueue() {
		messageQueueService.createMessageQueue();
	}

}
