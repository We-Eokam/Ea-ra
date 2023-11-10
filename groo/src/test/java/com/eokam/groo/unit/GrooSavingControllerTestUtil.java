package com.eokam.groo.unit;

import java.time.LocalDateTime;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.presentation.dto.GrooSavingRequest;

public class GrooSavingControllerTestUtil {

	public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6MX0.b9AyXTApiN9ii7WMT1GO8h_wjWgGG5hsW11hXT3RXXk";

	public static GrooSavingRequest getSavingTypeNullDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.proofAccusationId(1L)
			.savedAt(LocalDateTime.of(2023, 1, 1, 1, 0))
			.build();
	}

	public static GrooSavingRequest getActivityTypeNullDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.proofAccusationId(1L)
			.savedAt(LocalDateTime.of(2023, 1, 1, 1, 0))
			.build();
	}
	public static GrooSavingRequest getProofAccusationIdNullDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.savedAt(LocalDateTime.of(2023, 1, 1, 1, 0))
			.build();
	}
	public static GrooSavingRequest getProofAccusationIdNegativeDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.proofAccusationId(-1L)
			.savedAt(LocalDateTime.of(2023, 1, 1, 1, 0))
			.build();
	}
	public static GrooSavingRequest getSavedAtNullDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.proofAccusationId(1L)
			.build();
	}
	public static GrooSavingRequest getSavedAtFutureDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.proofAccusationId(1L)
			.savedAt(LocalDateTime.of(2030, 1, 1, 1, 0))
			.build();
	}
	public static GrooSavingRequest getSavingTypeNotMatchDto() {
		return GrooSavingRequest.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE)
			.proofAccusationId(1L)
			.savedAt(LocalDateTime.of(2023, 1, 1, 1, 0))
			.build();
	}
}
