package com.eokam.cpoint.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.cpoint.application.dto.ActivityStroeClassDto;
import com.eokam.cpoint.application.dto.StoreClassDto;
import com.eokam.cpoint.application.dto.StoreDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.global.ErrorCode;
import com.eokam.cpoint.global.exception.BusinessException;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.infra.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	private final CompanyRepository companyRepository;

	@Override
	@Transactional(readOnly = true)
	public List<StoreClassDto> retrieveNearCpointStore(Integer radius, Double latitude, Double longitude) {

		List<StoreDto> storeDtoList = storeRepository
			.getStoresListByCurrentPositionAndRadius(latitude, longitude, radius);

		List<StoreClassDto> storeClassDtoList = storeDtoList.stream()
			.map(storeDto -> {
				return companyRepository.findById(storeDto.getCompanyId())
					.map(company -> StoreClassDto.of(storeDto, company))
					.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));
			})
			.collect(Collectors.toList());

		return storeClassDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreClassDto> retrieveNearCpointStoreByActivityType(Integer radius, Double latitude, Double longitude,
		ActivityType activityType) {
		List<StoreDto> storeDtoList = storeRepository
			.getStoresListByCurrentPositionAndRadiusAndActivityType(latitude, longitude, radius, activityType);

		List<StoreClassDto> storeClassDtoList = storeDtoList.stream()
			.map(storeDto -> {
				return companyRepository.findById(storeDto.getCompanyId())
					.map(company -> StoreClassDto.of(storeDto, company))
					.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));
			})
			.collect(Collectors.toList());

		return storeClassDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityStroeClassDto> retrieveNearCpointStoreCategorizedByActivityType(Integer radius, Double latitude,
		Double longitude) {
		List<ActivityStroeClassDto> activityStroeClassDtoList = new ArrayList<>();
		for (ActivityType activityType : ActivityType.values()) {
			List<StoreClassDto> storeClassDtoList =
				retrieveNearCpointStoreByActivityType(radius, latitude, longitude, activityType);

			if (storeClassDtoList.isEmpty())
				continue;

			activityStroeClassDtoList
				.add(ActivityStroeClassDto
					.builder()
					.activityType(activityType)
					.stores(storeClassDtoList)
					.build());
		}
		return activityStroeClassDtoList;
	}
}
