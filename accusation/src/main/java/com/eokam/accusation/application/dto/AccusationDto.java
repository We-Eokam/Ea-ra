package com.eokam.accusation.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.presentation.dto.AccusationRequest;

import lombok.Builder;

@Builder
public record AccusationDto(Long accusationId, Long witnessId, Long memberId, ActivityType activityType,
							String activityDetail, List<String> imageList, LocalDateTime createdAt) {
	public static AccusationDto of(AccusationRequest request) {
		return AccusationDto.builder()
			.witnessId(request.getWitnessId())
			.memberId(request.getMemberId())
			.activityType(request.getActivityType())
			.activityDetail(request.getActivityDetail())
			.build();
	}

	public static AccusationDto of(Accusation accusation, List<String> fileUrls) {
		return AccusationDto.builder()
			.accusationId(accusation.getAccusationId())
			.witnessId(accusation.getWitnessId())
			.memberId(accusation.getMemberId())
			.activityType(accusation.getActivityType())
			.activityDetail(accusation.getActivityDetail())
			.imageList(fileUrls)
			.createdAt(accusation.getCreatedAt())
			.build();
	}
}