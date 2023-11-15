package com.eokam.proof.infrastructure.external.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record IsFollowRequest(Long memberId) {
	public static IsFollowRequest from(Long memberId) {
		return IsFollowRequest.builder()
			.memberId(memberId)
			.build();
	}
}
