package com.eokam.groo.presentation.dto;

import java.util.Optional;

import com.eokam.groo.infrastructure.dto.GrooTodayCountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TodayGrooSavingResponse {
	private Long proofCount;
	private Long accusationCount;

	public static TodayGrooSavingResponse from(GrooTodayCountDto grooTodayCountDto) {
		return TodayGrooSavingResponse.builder()
			.accusationCount(Optional.ofNullable(grooTodayCountDto.getAccusationCount()).orElse(0L))
			.proofCount(Optional.ofNullable(grooTodayCountDto.getProofCount()).orElse(0L))
			.build();
	}
}
