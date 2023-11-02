package com.eokam.cpoint.infra;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eokam.cpoint.application.dto.StoreDto;
import com.eokam.cpoint.domain.ActivityType;

@Repository
public interface MysqlStoreRepository extends StoreRepository {
	@Override
	@Query(value = "SELECT company_id,branch,"
		+ " ST_Distance_Sphere(POINT(:longitude,:latitude), POINT(longitude, latitude)) AS distance"
		+ " FROM Store "
		+ " WHERE ST_Distance_Sphere(POINT(:longitude,:latitude), POINT(longitude, latitude)) <= :radius"
		+ " ORDER BY distance", nativeQuery = true)
	public List<StoreDto> getStoresListByCurrentPositionAndRadius(Double latitude, Double longitude,
		Integer radius);

	@Override
	@Query(value = "SELECT s.company_id AS companyId,s.branch AS branch,"
		+ " ST_Distance_Sphere(POINT(:longitude,:latitude), POINT(s.longitude, s.latitude)) AS distance"
		+ " FROM Store as s"
		+ " INNER JOIN Company_Policy as cp"
		+ " ON cp.company_id = s.company_id"
		+ " AND cp.activity_type = :#{#activityType.name()}"
		+ " WHERE ST_Distance_Sphere(POINT(:longitude,:latitude), POINT(s.longitude, s.latitude)) <= :radius"
		+ " ORDER BY distance", nativeQuery = true)
	List<StoreDto> getStoresListByCurrentPositionAndRadiusAndActivityType(Double latitude, Double longitude,
		Integer radius, ActivityType activityType);
}
