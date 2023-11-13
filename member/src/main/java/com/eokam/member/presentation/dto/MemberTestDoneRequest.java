package com.eokam.member.presentation.dto;

import com.eokam.member.domain.SavingType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberTestDoneRequest {

	@NotNull(message = "member_id가 필요합니다.")
	@Positive(message = "member_id는 양수여야 합니다.")
	private Long memberId;

	@NotNull(message = "적립할 그루가 필요합니다")
	@Positive(message = "적립할 그루는 양수여야합니다")
	private Integer groo;
}
