package com.eokam.groo.application.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record GrooDailyDto(LocalDate date, Long proofCount) {

	public static GrooDailyDto of(LocalDate localDate, Long count) {
		return GrooDailyDto.builder()
			.date(localDate)
			.proofCount(count != null ? count : 0L)
			.build();
	}
}
