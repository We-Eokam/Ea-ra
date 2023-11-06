package com.eokam.cpoint.application.service;

import java.util.List;

import com.eokam.cpoint.application.dto.StoreClassDto;
import com.eokam.cpoint.domain.ActivityType;

public interface StoreService {

	public List<StoreClassDto> retrieveNearCpointStore(Integer radius, Double latitude, Double longitude);

	public List<StoreClassDto> retrieveNearCpointStoreByActivityType(Integer radius, Double latitude, Double longitude,
		ActivityType activityType);

}
