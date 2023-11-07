package com.eokam.groo.presentation.dto;

import java.util.List;

import com.eokam.groo.infrastructure.dto.WeeklyProofCountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class WeeklyProofCountResponse {
	private List<DailyProofCountResponse> grooSavingList;

	public static WeeklyProofCountResponse from(List<WeeklyProofCountDto> weeklyProofCountDto){
		return WeeklyProofCountResponse.builder()
			.grooSavingList(weeklyProofCountDto.stream().map(DailyProofCountResponse::from).toList())
			.build();
	}
}
