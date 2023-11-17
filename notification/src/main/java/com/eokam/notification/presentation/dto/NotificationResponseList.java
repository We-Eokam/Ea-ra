package com.eokam.notification.presentation.dto;

import java.util.List;

import com.eokam.notification.application.notification.dto.NotificationDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationResponseList {
	private List<NotificationResponse> notificationList;

	public static NotificationResponseList from(List<NotificationDto> notificationList) {
		return NotificationResponseList.builder()
			.notificationList(
				notificationList.stream()
					.map(NotificationResponse::from)
					.toList()
			).build();
	}
}
