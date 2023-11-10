package com.eokam.accusation.presentation.dto;

import java.time.LocalDateTime;

import com.eokam.accusation.global.constant.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class GrooSavingRequest {
	private Long memberId;

	private String savingType;

	private ActivityType activityType;

	private Long proofAccusationId;

	private LocalDateTime savedAt;

	public static GrooSavingRequest from(AccusationResponse response) {
		return GrooSavingRequest.builder()
			.memberId(response.getMemberId())
			.savingType("ACCUSATION")
			.activityType(response.getActivityType())
			.proofAccusationId(response.getAccusationId())
			.savedAt(response.getCreatedAt())
			.build();
	}
}
