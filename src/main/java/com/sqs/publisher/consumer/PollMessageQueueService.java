package com.sqs.publisher.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollMessageQueueService {

	private final AmazonSQS amazonSQSClient;

	@Value("${app.config.message.queue.topic}")
	private String messageQueueTopic;

	@Scheduled(fixedDelay = 10000)
	public void receiveMessage() {
		try {
			String queueUrl = amazonSQSClient.getQueueUrl(messageQueueTopic).getQueueUrl();
			ReceiveMessageResult receiveMessage = amazonSQSClient.receiveMessage(queueUrl);
			if (!receiveMessage.getMessages().isEmpty()) {
				Message message = receiveMessage.getMessages().get(0);
				log.info("Messages is {} ", message.getBody());
				amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
			}
		} catch (QueueDoesNotExistException e) {
			e.printStackTrace();
		}
	}

}
