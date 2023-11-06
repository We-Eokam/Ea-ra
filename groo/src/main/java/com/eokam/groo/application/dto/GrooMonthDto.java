package com.eokam.groo.application.dto;

import java.util.List;

import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;
import com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto;

import lombok.Builder;

@Builder
public record GrooMonthDto(Long proofSum, Long proofCount, Long accusationSum, Long accusationCount, List<GrooDailySumAmountDto> grooSavingList) {

	public static GrooMonthDto of(List<GrooDailySumAmountDto> dailySumAndAmount, GrooMonthSumAmountDto grooMonthSumAmountDto) {
		return GrooMonthDto.builder()
			.proofSum(grooMonthSumAmountDto.getProofSum())
			.proofCount(grooMonthSumAmountDto.getProofCount())
			.accusationSum(grooMonthSumAmountDto.getAccusationSum())
			.accusationCount(grooMonthSumAmountDto.getAccusationCount())
			.grooSavingList(dailySumAndAmount)
			.build();
	}

}
