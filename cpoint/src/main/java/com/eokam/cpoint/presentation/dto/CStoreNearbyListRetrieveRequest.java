package com.eokam.cpoint.presentation.dto;

import jakarta.validation.constraints.NotNull;
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

	@NotNull(message = "latitude가 필요합니다.")
	Double latitude;

	@NotNull(message = "longitude가 필요합니다.")
	Double longitude;

	@NotNull(message = "radius가 필요합니다.")
	Integer radius;
}
