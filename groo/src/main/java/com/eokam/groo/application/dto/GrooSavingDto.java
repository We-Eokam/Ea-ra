package com.eokam.groo.application.dto;

import java.time.LocalDateTime;

import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.presentation.dto.GrooSavingRequest;

import lombok.Builder;

@Builder
public record GrooSavingDto(Long savingId, Long memberId, Long amount, Long remainGroo, Long proofAccusationId, SavingType savingType, ActivityType activityType, LocalDateTime savedAt) {

	public static GrooSavingDto from(GrooSaving grooSaving) {
		return GrooSavingDto.builder()
			.savingId(grooSaving.getSavingId())
			.memberId(grooSaving.getMemberId())
			.amount(grooSaving.getAmount())
			.savingType(grooSaving.getSavingType())
			.proofAccusationId(grooSaving.getProofAccusationId())
			.activityType(grooSaving.getActivityType())
			.remainGroo(grooSaving.getRemainGroo())
			.savedAt(grooSaving.getSavedAt())
			.build();
	}

	public static GrooSavingDto of(GrooSavingRequest request) {
		return GrooSavingDto.builder()
			.memberId(request.getMemberId())
			.savingType(request.getSavingType())
			.activityType(request.getActivityType())
			.proofAccusationId(request.getProofAccusationId())
			.savedAt(request.getSavedAt())
			.build();
	}
}
