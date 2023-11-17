package com.eokam.groo.infrastructure.dto;

import lombok.Getter;

@Getter
public class GrooTodayCountDto {
	private Long proofCount;
	private Long accusationCount;

	public GrooTodayCountDto(Long proofCount, Long accusationCount) {
		this.proofCount = proofCount;
		this.accusationCount = accusationCount;
	}
}
