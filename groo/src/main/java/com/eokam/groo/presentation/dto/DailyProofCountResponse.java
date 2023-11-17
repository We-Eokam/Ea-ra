package com.eokam.groo.presentation.dto;

import java.time.LocalDate;

import com.eokam.groo.application.dto.GrooDailyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DailyProofCountResponse {
	private LocalDate date;
	private Long proofCount;

	public static DailyProofCountResponse from(GrooDailyDto grooDailyDto){
		return DailyProofCountResponse.builder()
			.date(grooDailyDto.date())
			.proofCount(grooDailyDto.proofCount())
			.build();
	}
}
