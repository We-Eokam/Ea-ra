package com.eokam.groo.presentation.dto;

import java.time.LocalDateTime;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.global.validation.TypeMatchCheck;
import com.eokam.groo.global.validation.ValidationGroups;

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
@TypeMatchCheck(savingType = "savingType", activityType = "activityType", groups = ValidationGroups.OtherCheckGroup.class)
public class GrooSavingRequest {
	@NotNull(groups = ValidationGroups.NotNullGroup.class)
	@Positive(groups = ValidationGroups.OtherCheckGroup.class)
	private Long memberId;

	@NotNull(groups = ValidationGroups.NotNullGroup.class)
	private SavingType savingType;

	@NotNull(groups = ValidationGroups.NotNullGroup.class)
	private ActivityType activityType;

	@NotNull(groups = ValidationGroups.NotNullGroup.class)
	@Positive(groups = ValidationGroups.OtherCheckGroup.class)
	private Long proofAccusationId;

	@NotNull(groups = ValidationGroups.NotNullGroup.class)
	@Past(groups = ValidationGroups.OtherCheckGroup.class)
	private LocalDateTime savedAt;
}
