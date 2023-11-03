package com.eokam.groo.presentation.dto;

import java.time.LocalDateTime;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GrooSavingRequest {
	private SavingType savingType;
	private ActivityType activityType;
	private Long proofAccusationId;
	private LocalDateTime savedAt;
}
