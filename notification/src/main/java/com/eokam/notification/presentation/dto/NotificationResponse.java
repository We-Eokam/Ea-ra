package com.eokam.notification.presentation.dto;

import java.time.LocalDateTime;

import com.eokam.notification.application.constant.NotificationType;
import com.eokam.notification.application.notification.dto.NotificationDto;
import com.eokam.notification.infrastructure.accusation.constant.AccusationType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationResponse {
	private final NotificationType notificationType;
	private final Long sender;
	private final Long receiver;
	private final String content;
	private final LocalDateTime createdAt;
	private final AccusationType accusationType;

	public static NotificationResponse from(NotificationDto notificationDto) {
		return NotificationResponse.builder()
			.notificationType(notificationDto.getNotificationType())
			.sender(notificationDto.getSender())
			.receiver(notificationDto.getReceiver())
			.content(notificationDto.getContent())
			.createdAt(notificationDto.getCreatedAt())
			.accusationType(notificationDto.getAccusationType())
			.build();
	}
}
