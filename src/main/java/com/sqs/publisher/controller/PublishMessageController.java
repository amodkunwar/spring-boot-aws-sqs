package com.sqs.publisher.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.InvalidMessageContentsException;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.sqs.publisher.dto.CreateExpenseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublishMessageController {

	@Value("${app.config.message.queue.topic}")
	private String messageQueueTopic;

	private final AmazonSQS amazonSQSClient;

	@PostMapping("/publish")
	public ResponseEntity<Object> publishExpense(@RequestBody CreateExpenseDto createExpenseDto) {
		CompletableFuture.runAsync(() -> {

			try {
				GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(messageQueueTopic);
				log.info("Reading SQS Queue done: URL {}", queueUrl.getQueueUrl());
				amazonSQSClient.sendMessage(queueUrl.getQueueUrl(),
						createExpenseDto.getType() + ":" + createExpenseDto.getAmount());
			} catch (QueueDoesNotExistException | InvalidMessageContentsException e) {
				log.error("Queue does not exist {}", e.getMessage());
			}

		});
		return new ResponseEntity<>("Request accepted!!", HttpStatusCode.valueOf(201));
	}

}
