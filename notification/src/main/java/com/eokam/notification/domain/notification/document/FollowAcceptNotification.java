package com.eokam.notification.domain.notification.document;

import java.time.LocalDateTime;

import com.eokam.notification.application.constant.NotificationType;
import com.eokam.notification.application.notification.dto.NotificationDto;

import lombok.Builder;

public class FollowAcceptNotification extends Notification {
	@Builder
	public FollowAcceptNotification(NotificationType notificationType,
		Long sender, Long receiver, String content, LocalDateTime createdAt) {
		super(notificationType, sender, receiver, content, createdAt);
	}

	public static FollowAcceptNotification from(NotificationDto dto) {
		return FollowAcceptNotification.builder()
			.notificationType(dto.getNotificationType())
			.sender(dto.getSender())
			.receiver(dto.getReceiver())
			.content("회원님의 친구 요청을 수락했어요.")
			.createdAt(LocalDateTime.now())
			.build();
	}
}
