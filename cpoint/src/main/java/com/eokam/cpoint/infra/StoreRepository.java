package com.eokam.cpoint.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.eokam.cpoint.application.dto.StoreDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Store;

@NoRepositoryBean
public interface StoreRepository extends JpaRepository<Store, Long> {
	public List<StoreDto> getStoresListByCurrentPositionAndRadius(Double latitude, Double longitude,
		Integer radius);

	public List<StoreDto> getStoresListByCurrentPositionAndRadiusAndActivityType(Double latitude, Double longitude,
		Integer radius, ActivityType activityType);
}
