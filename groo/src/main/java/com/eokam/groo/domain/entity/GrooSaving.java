package com.eokam.groo.domain.entity;

import java.time.LocalDateTime;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GrooSaving {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long savingId;

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = false)
	private Long remainGroo;

	@Column(nullable = false)
	private SavingType savingType;

	@Column(nullable = false)
	private ActivityType activityType;

	@Column(nullable = false)
	private LocalDateTime savedAt;
}
