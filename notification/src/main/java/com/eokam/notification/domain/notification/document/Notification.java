package com.eokam.notification.domain.notification.document;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.eokam.notification.application.constant.NotificationType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "superBuilder")
@Document("notification")
public class Notification {
	@Field("notification_type")
	private NotificationType notificationType;

	private Long sender;

	private Long receiver;

	private String content;

	@Field("created_at")
	private LocalDateTime createdAt;
}
