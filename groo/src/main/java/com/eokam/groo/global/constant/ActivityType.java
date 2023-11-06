package com.eokam.groo.global.constant;

import lombok.Getter;

@Getter
public enum ActivityType {

	// Accusation
	DISPOSABLE(250L),
	PAPER(150L),
	ELECTRICITY(350L),
	WATER(400L),
	FOOD(300L),
	OTHER(200L),

	// Proof

	ELECTRONIC_RECEIPT(135L),
	TUMBLER(410L),
	DISPOSABLE_CUP(275L),
	DISCARDED_PHONE(1370L),
	ECO_FRIENDLY_PRODUCTS(1370L),
	EMISSION_FREE_CAR(685L),
	HIGH_QUALITY_RECYCLED_PRODUCTS(315L),
	MULTI_USER_CONTAINER(1370L),
	REFILL_STATION(2700L),
	ETC(150L)
	;

	private Long savingAmount;

	ActivityType(Long savingAmount) {
		this.savingAmount = savingAmount;
	}
}
