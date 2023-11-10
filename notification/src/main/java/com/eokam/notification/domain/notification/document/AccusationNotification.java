package com.eokam.notification.domain.notification.document;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Field;

import com.eokam.notification.application.constant.NotificationType;
import com.eokam.notification.application.notification.dto.NotificationDto;
import com.eokam.notification.infrastructure.accusation.constant.AccusationType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccusationNotification extends Notification {
	@Field("accusation_type")
	@Enumerated(EnumType.STRING)
	private AccusationType accusationType;

	@Builder
	public AccusationNotification(NotificationType notificationType,
		Long sender, Long receiver, String content, LocalDateTime createdAt, AccusationType accusationType) {
		super(notificationType, sender, receiver, content, createdAt);
		this.accusationType = accusationType;
	}

	public static AccusationNotification from(NotificationDto dto) {
		return AccusationNotification.builder()
			.notificationType(dto.getNotificationType())
			.sender(dto.getSender())
			.receiver(dto.getReceiver())
			.content(dto.getSender() + "님이 회원님의 환경 오염 활동을 목격했어요.")
			.createdAt(LocalDateTime.now())
			.accusationType(dto.getAccusationType())
			.build();
	}
}
