package com.eokam.proof.domain.constant;

public enum ActivityType {
	ELECTRONIC_RECEIPT("전자영수증"),
	TUMBLER("텀블러"),
	DISPOSABLE_CUP("일회용컵 반환"),
	DISCARDED_PHONE("폐휴대폰"),
	FUTURE_GENERATION("미래세대실천"),
	ECO_FRIENDLY_PRODUCTS("친환경제품"),
	EMISSION_FREE_CAR("무공해차"),
	HIGH_QUALITY_RECYCLED_PRODUCTS("고품질 재활용품"),
	MULTI_USE_CONTAINER("다회용기"),
	REFILL_STATION("리필스테이션"),
	ETC("기타");

	private final String name;

	ActivityType(String name) {
		this.name = name;
	}
}
