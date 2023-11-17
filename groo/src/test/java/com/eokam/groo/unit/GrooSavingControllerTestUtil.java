package com.eokam.groo.unit;

import java.time.LocalDateTime;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.presentation.dto.GrooSavingRequest;

public class GrooSavingControllerTestUtil {

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
			.activityType(ActivityType.PLASTIC)
			.proofAccusationId(1L)
			.savedAt(LocalDateTime.of(2023, 1, 1, 1, 0))
			.build();
	}
}
