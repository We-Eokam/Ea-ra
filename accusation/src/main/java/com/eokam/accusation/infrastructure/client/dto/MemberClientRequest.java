package com.eokam.accusation.infrastructure.client.dto;

import com.eokam.accusation.application.dto.AccusationDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberClientRequest {
	private Long witnessId;
	private Long memberId;

	public static MemberClientRequest from(AccusationDto accusationDto) {
		return MemberClientRequest.builder()
			.witnessId(accusationDto.witnessId())
			.memberId(accusationDto.memberId())
			.build();
	}

	public static MemberClientRequest of(Long targetId, Long memberId) {
		return MemberClientRequest.builder()
			.witnessId(memberId)
			.memberId(targetId)
			.build();

	}
}
