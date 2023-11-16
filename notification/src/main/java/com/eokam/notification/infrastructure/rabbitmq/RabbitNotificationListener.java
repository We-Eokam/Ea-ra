package com.eokam.notification.infrastructure.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eokam.notification.application.notification.dto.NotificationDto;
import com.eokam.notification.application.notification.service.NotificationService;
import com.eokam.notification.application.token.service.FcmTokenService;
import com.eokam.notification.infrastructure.fcm.service.FcmMessageService;
import com.eokam.notification.presentation.dto.accusation.AccusationRequest;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitNotificationListener {

	private final FcmTokenService fcmService;
	private final FcmMessageService fcmMessageService;
	private final NotificationService notificationService;

	@RabbitListener(queues = "notificationQueue")
	public void receiveMessage(AccusationRequest accusationRequest) throws FirebaseMessagingException {
		NotificationDto notificationDto = notificationService.saveAccusationNotification(
			NotificationDto.accusation(accusationRequest));
		log.info("Data has been successfully saved to the Notification. Saved Data: '{}'", notificationDto);
		fcmMessageService.sendMessageTo(
			fcmService.getToken(notificationDto.getReceiver()).token(),
			"환경 오염 제보",
			notificationDto.getContent()
		);
		log.info("Message sent successfully. Message: '{}'", notificationDto.getContent());
	}

}
