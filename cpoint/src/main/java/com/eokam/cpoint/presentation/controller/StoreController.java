package com.eokam.cpoint.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.cpoint.application.dto.ActivityStroeClassDto;
import com.eokam.cpoint.application.service.StoreService;
import com.eokam.cpoint.presentation.dto.ActivityTypeStoreListReponse;
import com.eokam.cpoint.presentation.dto.CStoreNearbyListRetrieveRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cpoint/store")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping()
	public ResponseEntity<ActivityTypeStoreListReponse> retrieveActivityTypeStoreList(
		@Valid @ModelAttribute CStoreNearbyListRetrieveRequest storeRequest) {
		List<ActivityStroeClassDto> activityStroeClassDtoList
			= storeService.retrieveNearCpointStoreCategorizedByActivityType(
			storeRequest.getRadius(), storeRequest.getLatitude(), storeRequest.getLongitude());
		return ResponseEntity
			.ok(ActivityTypeStoreListReponse.builder().storeList(activityStroeClassDtoList).build());
	}

}
