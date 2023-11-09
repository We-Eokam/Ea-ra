package com.eokam.accusation.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageAccusationDto {

	private PageInfoDto pageInfo;
	private List<AccusationDto> accusationDtoList;

	public static PageAccusationDto of(PageInfoDto pageInfo, List<AccusationDto> accusationDtoList) {
		return PageAccusationDto.builder()
			.pageInfo(pageInfo)
			.accusationDtoList(accusationDtoList)
			.build();
	}
}
