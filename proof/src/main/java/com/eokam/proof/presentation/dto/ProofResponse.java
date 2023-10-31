package com.eokam.proof.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.application.dto.ProofImageDto;
import com.eokam.proof.domain.constant.ActivityType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProofResponse(Long proofId, ActivityType activityType, Long cCompanyId,
							@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul") LocalDateTime createdAt,
							List<Picture> picture, String content) {

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
			.build();
	}
}
