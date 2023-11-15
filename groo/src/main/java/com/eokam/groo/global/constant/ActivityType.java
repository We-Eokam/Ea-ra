package com.eokam.groo.global.constant;

import lombok.Getter;

@Getter
public enum ActivityType {

	// Accusation
	PLASTIC(250L, SavingType.ACCUSATION),
	PAPER(150L, SavingType.ACCUSATION),
	ELECTRICITY(350L, SavingType.ACCUSATION),
	WATER(400L, SavingType.ACCUSATION),
	FOOD(300L, SavingType.ACCUSATION),
	OTHER(200L, SavingType.ACCUSATION),

	// Proof

	ELECTRONIC_RECEIPT(135L, SavingType.PROOF),
	TUMBLER(410L, SavingType.PROOF),
	DISPOSABLE_CUP(275L, SavingType.PROOF),
	DISCARDED_PHONE(1370L, SavingType.PROOF),
	ECO_FRIENDLY_PRODUCTS(1370L, SavingType.PROOF),
	EMISSION_FREE_CAR(685L, SavingType.PROOF),
	HIGH_QUALITY_RECYCLED_PRODUCTS(315L, SavingType.PROOF),
	MULTI_USER_CONTAINER(1370L, SavingType.PROOF),
	REFILL_STATION(2700L, SavingType.PROOF),
	ETC(150L, SavingType.PROOF)
	;

	private Long savingAmount;
	private SavingType savingType;

	ActivityType(Long savingAmount, SavingType savingType) {
		this.savingAmount = savingAmount;
		this.savingType = savingType;
	}
}
