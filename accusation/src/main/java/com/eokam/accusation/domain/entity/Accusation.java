package com.eokam.accusation.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.global.constant.ActivityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@EntityListeners(value = {AuditingEntityListener.class})
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accusation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accusationId;

	@Column(nullable = false)
	private Long witnessId;

	@Column(nullable = false)
	private Long memberId;

	@Enumerated(EnumType.STRING)
	private ActivityType activityType;

	private String activityDetail;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	public static Accusation from(AccusationDto accusationDto) {
		return Accusation.builder()
			.witnessId(accusationDto.witnessId())
			.memberId(accusationDto.memberId())
			.activityType(accusationDto.activityType())
			.activityDetail(accusationDto.activityDetail())
			.build();
	}

}