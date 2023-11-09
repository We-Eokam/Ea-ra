package com.eokam.accusation.presentation.dto;

import com.eokam.accusation.application.dto.PageInfoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PageInfoResponse {
	private Boolean isLast;
	private Integer totalPages;
	private Long totalElements;

	public static PageInfoResponse from(PageInfoDto pageInfoDto) {
		return PageInfoResponse.builder()
			.isLast(pageInfoDto.getIsLast())
			.totalPages(pageInfoDto.getTotalPages())
			.totalElements(pageInfoDto.getTotalElements())
			.build();
	}
}
