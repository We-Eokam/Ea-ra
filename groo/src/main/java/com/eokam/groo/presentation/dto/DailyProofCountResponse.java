package com.eokam.groo.presentation.dto;

import java.sql.Date;

import com.eokam.groo.infrastructure.dto.WeeklyProofCountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DailyProofCountResponse {
	private Date date;
	private Long proofCount;

	public static DailyProofCountResponse from(WeeklyProofCountDto weeklyProofCountDto){
		return DailyProofCountResponse.builder()
			.date(weeklyProofCountDto.getDate())
			.proofCount(weeklyProofCountDto.getProofCount())
			.build();
	}
}
