package com.eokam.groo.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.eokam.groo.infrastructure.dto.RepayGrooRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitSender {
	private final RabbitTemplate template;

	public void send(RepayGrooRequest request) {
		template.convertAndSend("repayGrooQueue", request);
		log.info("Message {} sent successfully to queue repayGrooQueue", request);
	}

}
