package com.eokam.accusation.presentation.dto;

import com.eokam.accusation.global.constant.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationRequest {
	private Long sender;
	private Long receiver;
	private ActivityType accusationType;

	public static NotificationRequest from(AccusationResponse response) {
		return NotificationRequest.builder()
			.sender(response.getWitnessId())
			.receiver(response.getMemberId())
			.accusationType(response.getActivityType())
			.build();
	}
}
