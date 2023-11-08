package com.eokam.cpoint.application.dto;

import java.util.List;

import com.eokam.cpoint.domain.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ActivityStroeClassDto {

	private ActivityType activityType;

	private List<StoreClassDto> stores;
}
