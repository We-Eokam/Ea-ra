package com.eokam.proof.application.dto;

import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.infrastructure.external.s3.S3FileDetail;

import lombok.Builder;

@Builder
public record ProofImageDto(Long proofImageId, String fileName, String fileUrl) {

	public static ProofImageDto from(ProofImage proofImage) {
		return ProofImageDto.builder()
			.proofImageId(proofImage.getProofImageId())
			.fileName(proofImage.getFileName())
			.fileUrl(proofImage.getFileUrl())
			.build();
	}

	public static ProofImageDto from(S3FileDetail fileDetail) {
		return ProofImageDto.builder()
			.fileName(fileDetail.originalFileName())
			.fileUrl(fileDetail.url())
			.build();
	}

}
