package com.eokam.accusation.presentation.dto;

import com.eokam.accusation.global.constant.ActivityType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccusationRequest {

	private Long targetId;

	private ActivityType activityType;

	private String activityDetail;

}