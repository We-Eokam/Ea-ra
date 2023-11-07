package com.eokam.groo.presentation.dto;

import java.util.List;

import com.eokam.groo.application.dto.GrooMonthDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GrooSavingMonthResponse {
	private Long proofSum;
	private Long proofCount;
	private Long accusationSum;
	private Long accusationCount;
	private List<GrooSavingDailyResponse> grooSavingList;
	public static GrooSavingMonthResponse from(GrooMonthDto grooMonthDto){
		return GrooSavingMonthResponse.builder()
			.proofSum(grooMonthDto.proofSum())
			.proofCount(grooMonthDto.proofCount())
			.accusationSum(grooMonthDto.accusationSum())
			.accusationCount(grooMonthDto.accusationCount())
			.grooSavingList(grooMonthDto.grooSavingList().stream().map(GrooSavingDailyResponse::from).toList())
			.build();
	}
}
