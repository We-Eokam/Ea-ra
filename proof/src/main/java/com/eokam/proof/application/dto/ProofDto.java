package com.eokam.proof.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.infrastructure.external.s3.S3FileDetail;

import lombok.Builder;

@Builder
public record ProofDto(Long proofId, Long memberId, ActivityType activityType, Long cCompanyId, LocalDateTime createdAt,
					   String content, List<ProofImageDto> proofImages) {
	public static ProofDto from(Proof proof) {
		return ProofDto.builder()
			.proofId(proof.getProofId())
			.memberId(proof.getMemberId())
			.activityType(proof.getActivityType())
			.cCompanyId(proof.getCCompanyId())
			.createdAt(proof.getCreatedAt())
			.content(proof.getContents())
			.proofImages(
				proof.getProofImages().stream()
					.map(ProofImageDto::from)
					.toList()
			)
			.build();
	}

	public static ProofDto of(Proof proof, List<S3FileDetail> s3FileList) {
		return ProofDto.builder()
			.proofId(proof.getProofId())
			.memberId(proof.getMemberId())
			.activityType(proof.getActivityType())
			.cCompanyId(proof.getCCompanyId())
			.createdAt(proof.getCreatedAt())
			.content(proof.getContents())
			.proofImages(
				s3FileList.stream()
					.map(ProofImageDto::from)
					.toList()
			)
			.build();
	}

	public static Page<ProofDto> toDtoPage(Page<Proof> page) {
		return page.map(ProofDto::from);
	}
}
