package com.eokam.accusation.global.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.eokam.accusation.presentation.dto.GrooSavingRequest;
import com.eokam.accusation.presentation.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitSender {

	private final RabbitTemplate template;

	public void send(GrooSavingRequest request) {
		template.convertAndSend("grooSavingQueue", request);
		log.info("Message {} sent successfully to queue grooSavingQueue", request);
	}

	public void send(NotificationRequest request) {
		template.convertAndSend("notificationQueue", request);
		log.info("Message {} sent successfully to queue notificationQueue", request);
	}
}
