package com.eokam.proof.application.dto;

import com.eokam.proof.domain.entity.ProofImage;

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

}
