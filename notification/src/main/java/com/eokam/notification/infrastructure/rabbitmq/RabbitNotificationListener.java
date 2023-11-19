package com.eokam.notification.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eokam.notification.application.notification.dto.NotificationDto;
import com.eokam.notification.application.notification.service.NotificationService;
import com.eokam.notification.application.token.service.FcmTokenService;
import com.eokam.notification.infrastructure.fcm.service.FcmMessageService;
import com.eokam.notification.infrastructure.member.MemberServiceFeign;
import com.eokam.notification.presentation.dto.accusation.AccusationRequest;
import com.eokam.notification.presentation.dto.follow.FollowRequest;
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
	private final MemberServiceFeign memberServiceFeign;

	@RabbitListener(queues = "notificationQueue")
	public void receiveMessage(AccusationRequest accusationRequest) {
		NotificationDto notificationDto = notificationService.saveAccusationNotification(
			NotificationDto.accusation(accusationRequest));
		log.info("Data has been successfully saved to the Notification. Saved Data: '{}'", notificationDto);

		String prefix =
			memberServiceFeign.getMemberDetail(accusationRequest.sender()).memberList().get(0).nickname() + "님이 ";

		try {
			fcmMessageService.sendMessageTo(
				fcmService.getToken(notificationDto.getReceiver()).token(),
				"환경 오염 제보",
				prefix + notificationDto.getContent()
			);
		} catch (FirebaseMessagingException e) {
			log.error("Firebase Cloud Messaging Token is Not exists: '{}'", notificationDto);
		}
		log.info("Message sent successfully. Message: '{}'", notificationDto.getContent());
	}

	@RabbitListener(queues = "followRequestQueue")
	public void receiveFollowMessage(FollowRequest followRequest) {
		NotificationDto notificationDto = notificationService.saveFollowNotification(
			NotificationDto.follow(followRequest));
		log.info("Data has been successfully saved to the Notification. Saved Data: '{}'", notificationDto);

		String prefix =
			memberServiceFeign.getMemberDetail(followRequest.sender()).memberList().get(0).nickname() + "님이 ";

		try {
			fcmMessageService.sendMessageTo(
				fcmService.getToken(notificationDto.getReceiver()).token(),
				"친구 요청",
				prefix + notificationDto.getContent()
			);
		} catch (FirebaseMessagingException e) {
			log.error("Firebase Cloud Messaging Token is Not exists: '{}'", notificationDto);
		}
		log.info("Message sent successfully. Message: '{}'", notificationDto.getContent());
	}

	@RabbitListener(queues = "followAcceptQueue")
	public void receiveAcceptMessage(FollowRequest followRequest) {
		NotificationDto notificationDto = notificationService.saveFollowAcceptNotification(
			NotificationDto.followAccept(followRequest));
		log.info("Data has been successfully saved to the Notification. Saved Data: '{}'", notificationDto);

		String prefix =
			memberServiceFeign.getMemberDetail(followRequest.sender()).memberList().get(0).nickname() + "님이 ";

		try {
			fcmMessageService.sendMessageTo(
				fcmService.getToken(notificationDto.getReceiver()).token(),
				"친구 요청 수락",
				prefix + notificationDto.getContent()
			);
		} catch (FirebaseMessagingException e) {
			log.error("Firebase Cloud Messaging Token is Not exists: '{}'", notificationDto);
		}
		log.info("Message sent successfully. Message: '{}'", notificationDto.getContent());
	}
}
