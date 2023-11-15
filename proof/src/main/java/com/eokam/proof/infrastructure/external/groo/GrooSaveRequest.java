package com.eokam.proof.infrastructure.external.groo;

import java.time.LocalDateTime;

import com.eokam.proof.domain.constant.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GrooSaveRequest(Long memberId, String savingType, ActivityType activityType, Long proofAccusationId,
							  LocalDateTime savedAt) {
	public static GrooSaveRequest of(Long memberId, ActivityType activityType, Long proofId,
		LocalDateTime savedAt) {
		return GrooSaveRequest.builder()
			.memberId(memberId)
			.savingType("PROOF")
			.activityType(activityType)
			.proofAccusationId(proofId)
			.savedAt(savedAt)
			.build();
	}
}
