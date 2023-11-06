package com.eokam.groo.presentation.dto;

import java.time.LocalDateTime;

import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GrooSavingResponse {

	private Long savingId;

	private Long memberId;

	private Long amount;

	private Long remainGroo;

	private SavingType savingType;

	private ActivityType activityType;

	private Long proofAccusationId;

	private LocalDateTime savedAt;

	public static GrooSavingResponse from(GrooSavingDto grooSavingDto) {
		return GrooSavingResponse.builder()
			.savingId(grooSavingDto.savingId())
			.memberId(grooSavingDto.memberId())
			.amount(grooSavingDto.amount())
			.savingType(grooSavingDto.savingType())
			.proofAccusationId(grooSavingDto.proofAccusationId())
			.activityType(grooSavingDto.activityType())
			.remainGroo(grooSavingDto.remainGroo())
			.savedAt(grooSavingDto.savedAt())
			.build();
	}
}
