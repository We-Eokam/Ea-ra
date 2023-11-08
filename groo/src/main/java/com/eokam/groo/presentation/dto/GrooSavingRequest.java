package com.eokam.groo.presentation.dto;

import java.time.LocalDateTime;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.global.validation.TypeMatchCheck;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeMatchCheck(savingType = "savingType", activityType = "activityType")
public class GrooSavingRequest {
	@NotNull
	private SavingType savingType;

	@NotNull
	private ActivityType activityType;

	@NotNull
	@Positive
	private Long proofAccusationId;

	@NotNull
	@Past
	private LocalDateTime savedAt;
}
