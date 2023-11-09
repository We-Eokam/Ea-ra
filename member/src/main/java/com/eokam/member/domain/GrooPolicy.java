package com.eokam.member.domain;

public class GrooPolicy {

	public static int getGrooAmountByActivityType(ActivityType activityType){
		return switch (activityType){
			case TUMBLER -> 410;
			case ELECTRONIC_RECEIPT -> 135;
			case DISPOSABLE_CUP -> 275;
			case DISCARDED_PHONE,ECO_FRIENDLY_PRODUCTS,MULTI_USE_CONTAINER -> 1370;
			case EMISSION_FREE_CAR -> 685;
			case HIGH_QUALITY_RECYCLED_PRODUCTS -> 315;
			case REFILL_STATION -> 2700;
			case ETC -> 150;
		};
	}

}
