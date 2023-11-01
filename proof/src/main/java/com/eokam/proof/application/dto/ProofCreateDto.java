package com.eokam.proof.application.dto;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.infrastructure.util.ParseJwtUtil;
import com.eokam.proof.presentation.dto.request.ProofCreateRequest;

import lombok.Builder;

@Builder
public record ProofCreateDto(Long memberId, ActivityType activityType, Long cCompanyId, String content) {
	public static ProofCreateDto of(String jwt, ProofCreateRequest proofCreateRequest) {
		return ProofCreateDto.builder()
			.memberId(ParseJwtUtil.parseMemberId(jwt))
			.activityType(proofCreateRequest.activityType())
			.cCompanyId(proofCreateRequest.cCompanyId())
			.content(proofCreateRequest.content())
			.build();
	}
}
