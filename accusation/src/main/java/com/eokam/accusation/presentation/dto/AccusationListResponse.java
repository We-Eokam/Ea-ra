package com.eokam.accusation.presentation.dto;

import java.util.List;

import com.eokam.accusation.application.dto.AccusationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccusationListResponse {

	private List<AccusationResponse> accusationList;

	public static AccusationListResponse from(List<AccusationDto> accusationDtoList) {
		return AccusationListResponse.builder()
			.accusationList(accusationDtoList.stream().map(AccusationResponse::from).toList())
			.build();
	}
}
