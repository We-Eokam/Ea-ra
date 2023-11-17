package com.eokam.proof.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.eokam.proof.infrastructure.rabbitmq.dto.GrooSavingRequest;

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
}
