package com.eokam.accusation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageInfoDto {
	private Boolean isLast;
	private Integer totalPages;
	private Long totalElements;

	public static PageInfoDto of(Boolean isLast, Integer totalPages, Long totalElements) {
		return PageInfoDto.builder()
			.isLast(isLast)
			.totalPages(totalPages)
			.totalElements(totalElements)
			.build();
	}
}
