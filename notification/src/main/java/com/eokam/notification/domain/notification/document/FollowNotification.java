package com.eokam.notification.domain.notification.document;

import java.time.LocalDateTime;

import com.eokam.notification.application.constant.NotificationType;
import com.eokam.notification.application.notification.dto.NotificationDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowNotification extends Notification {
	@Builder
	public FollowNotification(NotificationType notificationType,
		Long sender, Long receiver, String content, LocalDateTime createdAt) {
		super(notificationType, sender, receiver, content, createdAt);
	}

	public static FollowNotification of(NotificationDto dto, String senderNickname) {
		return FollowNotification.builder()
			.notificationType(dto.getNotificationType())
			.sender(dto.getSender())
			.receiver(dto.getReceiver())
			.content(senderNickname + "님이 회원님과 친구가 되고 싶어해요.")
			.createdAt(LocalDateTime.now())
			.build();
	}
}
