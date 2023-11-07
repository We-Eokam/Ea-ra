package com.eokam.groo.presentation.dto;

import java.sql.Date;

import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GrooSavingDailyResponse {
	private Date date;
	private Long proofSum;
	private Long proofCount;
	private Long accusationSum;
	private Long accusationCount;

	public static GrooSavingDailyResponse from(GrooDailySumAmountDto grooDailySumAmountDto){
		return GrooSavingDailyResponse.builder()
			.date(grooDailySumAmountDto.getDate())
			.proofSum(grooDailySumAmountDto.getProofSum())
			.proofCount(grooDailySumAmountDto.getProofCount())
			.accusationSum(grooDailySumAmountDto.getAccusationSum())
			.accusationCount(grooDailySumAmountDto.getAccusationCount())
			.build();
	}
}
