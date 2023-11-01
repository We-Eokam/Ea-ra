package com.eokam.cpoint.application.dto;

import com.eokam.cpoint.domain.ActivityType;

public class CpointSummaryDto {

	private ActivityType activityType;

	private Long point;

	public CpointSummaryDto(ActivityType activityType, Long point) {
		this.activityType = activityType;
		this.point = point;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public Long getPoint() {
		return point;
	}
}
