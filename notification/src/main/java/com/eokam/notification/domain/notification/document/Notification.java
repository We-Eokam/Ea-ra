package com.eokam.notification.domain.notification.document;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.eokam.notification.application.notification.dto.AccusationDto;
import com.eokam.notification.domain.notification.constant.NotificationType;
import com.eokam.notification.infrastructure.accusation.constant.AccusationType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Document("notification")
public class Notification {
	@Field("notification_type")
	private NotificationType notificationType;

	private Long sender;

	private Long receiver;

	@Field("accusation_type")
	@Enumerated(EnumType.STRING)
	private AccusationType accusationType;

	private String content;

	@Field("created_at")
	private LocalDateTime createdAt;

	public static Notification accusation(AccusationDto accusationDto) {
		// TODO: content 부분 Sender nickname 가져와서 해야됨
		return Notification.builder()
			.notificationType(NotificationType.ACCUSATION)
			.sender(accusationDto.sender())
			.receiver(accusationDto.receiver())
			.accusationType(accusationDto.accusationType())
			.content(accusationDto.sender() + "님이 회원님의 환경 오염 활동을 목격했어요.")
			.createdAt(LocalDateTime.now())
			.build();
	}
}
