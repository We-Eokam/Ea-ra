package com.eokam.groo.infrastructure.dto;

import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.global.constant.SavingType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RepayGrooRequest {
	private Long memberId;
	private SavingType savingType;
	private Long groo;

	public static RepayGrooRequest from(GrooSavingDto grooSavingDto) {
		return RepayGrooRequest.builder()
			.memberId(grooSavingDto.memberId())
			.savingType(grooSavingDto.savingType())
			.groo(grooSavingDto.amount())
			.build();
	}
}
