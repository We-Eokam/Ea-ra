package com.eokam.proof.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;

import lombok.Builder;

@Builder
public record ProofDto(Long proofId, Long memberId, ActivityType activityType, Long cCompanyId, LocalDateTime createdAt,
					   String content, List<ProofImage> proofImages) {
	public static ProofDto from(Proof proof) {
		return ProofDto.builder()
			.proofId(proof.getProofId())
			.memberId(proof.getMemberId())
			.activityType(proof.getActivityType())
			.cCompanyId(proof.getCCompanyId())
			.createdAt(proof.getCreatedAt())
			.content(proof.getContents())
			.proofImages(proof.getProofImages())
			.build();
	}
}
