package com.eokam.cpoint.application.dto;

import java.util.List;

import com.eokam.cpoint.domain.ActivityType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ActivityStroeClassDto {

	private ActivityType activityType;

	private List<StoreClassDto> stores;
}
