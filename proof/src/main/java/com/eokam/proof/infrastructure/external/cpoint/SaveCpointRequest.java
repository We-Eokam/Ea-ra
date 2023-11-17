package com.eokam.proof.infrastructure.external.cpoint;

import com.eokam.proof.domain.constant.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SaveCpointRequest(Long memberId, ActivityType activityType, Long companyId) {
	public static SaveCpointRequest of(Long memberId, ActivityType activityType, Long companyId) {
		return SaveCpointRequest.builder()
			.memberId(memberId)
			.activityType(activityType)
			.companyId(companyId)
			.build();
	}
}
