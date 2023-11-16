package com.eokam.member.infra.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.eokam.member.infra.dto.NotificationReqeust;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMQService {

	private final RabbitTemplate template;

	public void sendFollowRequestNotification(NotificationReqeust request) {
		template.convertAndSend("followRequestQueue", request);
		log.info("Message {} sent successfully to queue followRequestQueue", request);
	}

	public void sendFollowAcceptNotification(NotificationReqeust request) {
		template.convertAndSend("followAcceptQueue", request);
		log.info("Message {} sent successfully to queue followAcceptQueue", request);
	}
}
