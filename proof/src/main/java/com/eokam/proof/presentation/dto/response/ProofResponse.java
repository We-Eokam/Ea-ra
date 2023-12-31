package com.eokam.proof.presentation.dto.response;

import java.util.List;

import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.application.dto.ProofImageDto;
import com.eokam.proof.domain.constant.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProofResponse(Long proofId, Long memberId, ActivityType activityType, Long cCompanyId,
							String createdAt, List<Picture> picture, String content, Boolean isMine) {

	@Builder
	private record Picture(String url, String name) {
		private static Picture from(ProofImageDto proofImageDto) {
			return Picture.builder()
				.url(proofImageDto.fileUrl())
				.name(proofImageDto.fileName())
				.build();
		}
	}

	public static ProofResponse from(ProofDto proofDto) {
		return ProofResponse.builder()
			.proofId(proofDto.proofId())
			.memberId(proofDto.memberId())
			.activityType(proofDto.activityType())
			.cCompanyId(proofDto.cCompanyId())
			.createdAt(proofDto.createdAt())
			.picture(
				proofDto.proofImages()
					.stream()
					.map(Picture::from)
					.toList()
			)
			.content(proofDto.content())
			.isMine(proofDto.isMine())
			.build();
	}
}
