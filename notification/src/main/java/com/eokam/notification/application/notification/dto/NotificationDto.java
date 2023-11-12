package com.eokam.notification.application.notification.dto;

import java.time.LocalDateTime;

import com.eokam.notification.application.constant.NotificationType;
import com.eokam.notification.domain.notification.document.AccusationNotification;
import com.eokam.notification.domain.notification.document.Notification;
import com.eokam.notification.presentation.dto.accusation.AccusationRequest;
import com.eokam.notification.presentation.dto.accusation.AccusationType;
import com.eokam.notification.presentation.dto.follow.FollowRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDto {
	private final NotificationType notificationType;
	private final Long sender;
	private final Long receiver;
	private final String content;
	private final LocalDateTime createdAt;
	private final AccusationType accusationType;

	public static NotificationDto accusation(AccusationRequest accusationRequest) {
		return NotificationDto.builder()
			.notificationType(NotificationType.ACCUSATION)
			.sender(accusationRequest.sender())
			.receiver(accusationRequest.receiver())
			.accusationType(accusationRequest.accusationType())
			.build();
	}

	public static NotificationDto follow(FollowRequest followRequest) {
		return NotificationDto.builder()
			.notificationType(NotificationType.FOLLOW_REQUEST)
			.sender(followRequest.sender())
			.receiver(followRequest.receiver())
			.build();
	}

	public static NotificationDto followAccept(FollowRequest followRequest) {
		return NotificationDto.builder()
			.notificationType(NotificationType.FOLLOW_ACCEPT)
			.sender(followRequest.sender())
			.receiver(followRequest.receiver())
			.build();
	}

	public static NotificationDto from(Notification notification) {
		return NotificationDto.builder()
			.notificationType(notification.getNotificationType())
			.sender(notification.getSender())
			.receiver(notification.getReceiver())
			.content(notification.getContent())
			.createdAt(notification.getCreatedAt().plusHours(9L))
			.accusationType(notification instanceof AccusationNotification ?
				((AccusationNotification)notification).getAccusationType() : null)
			.build();
	}

}
