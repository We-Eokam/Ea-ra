package com.eokam.proof.infrastructure.external.member;

import lombok.Builder;

@Builder
public record IsFollowRequest(Long memberId) {
	public static IsFollowRequest from(Long memberId) {
		return IsFollowRequest.builder()
			.memberId(memberId)
			.build();
	}
}
