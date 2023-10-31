package com.eokam.cpoint.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CStoreNearbyListRetrieveRequest {

	Double latitude;

	Double longitude;

	Integer radius;
}
