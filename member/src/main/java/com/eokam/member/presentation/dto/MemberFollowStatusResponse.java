package com.eokam.member.presentation.dto;

import com.eokam.member.infra.dto.FollowStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberFollowStatusResponse {

	private Long memberId;
	private FollowStatus followStatus;

}
