package com.eokam.groo.infrastructure.dto;

import lombok.Getter;

@Getter
public class GrooMonthSumAmountDto {
	private Long proofSum;
	private Long proofCount;
	private Long accusationSum;
	private Long accusationCount;

	public GrooMonthSumAmountDto(Long proofSum, Long proofCount, Long accusationSum, Long accusationCount) {
		this.proofSum = proofSum;
		this.proofCount = proofCount;
		this.accusationSum = accusationSum;
		this.accusationCount = accusationCount;
	}
}
