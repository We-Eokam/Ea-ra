package com.eokam.proof.application.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.infrastructure.external.s3.S3FileDetail;

import lombok.Builder;

@Builder
public record ProofDto(Long proofId, Long memberId, ActivityType activityType, Long cCompanyId, String createdAt,
					   String content, List<ProofImageDto> proofImages, Boolean isMine) {
	public static ProofDto of(Proof proof, Boolean isMine) {
		return ProofDto.builder()
			.proofId(proof.getProofId())
			.memberId(proof.getMemberId())
			.activityType(proof.getActivityType())
			.cCompanyId(proof.getCCompanyId())
			.createdAt(proof.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.content(proof.getContents())
			.proofImages(
				proof.getProofImages().stream()
					.map(ProofImageDto::from)
					.toList()
			)
			.isMine(isMine)
			.build();
	}

	public static ProofDto of(Proof proof, List<S3FileDetail> s3FileList) {
		return ProofDto.builder()
			.proofId(proof.getProofId())
			.memberId(proof.getMemberId())
			.activityType(proof.getActivityType())
			.cCompanyId(proof.getCCompanyId())
			.createdAt(proof.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.content(proof.getContents())
			.proofImages(
				s3FileList.stream()
					.map(ProofImageDto::from)
					.toList()
			)
			.build();
	}

	public static Page<ProofDto> toDtoPage(Page<Proof> page, Long memberId) {
		return page.map(proof -> ProofDto.of(proof, memberId.equals(proof.getMemberId())));
	}

}
