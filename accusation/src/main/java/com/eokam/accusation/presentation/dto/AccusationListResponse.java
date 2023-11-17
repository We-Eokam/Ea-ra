package com.eokam.accusation.presentation.dto;

import java.util.List;

import com.eokam.accusation.application.dto.PageAccusationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccusationListResponse {

	private PageInfoResponse pageInfo;
	private List<AccusationResponse> accusationList;

	public static AccusationListResponse from(PageAccusationDto pageAccusationDto) {
		return AccusationListResponse.builder()
			.pageInfo(PageInfoResponse.from(pageAccusationDto.getPageInfo()))
			.accusationList(pageAccusationDto.getAccusationDtoList().stream().map(AccusationResponse::from).toList())
			.build();
	}
}
