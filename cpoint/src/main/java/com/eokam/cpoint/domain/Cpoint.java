package com.eokam.cpoint.domain;

import java.time.LocalDateTime;

import com.eokam.cpoint.application.dto.CpointDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cpoint extends BaseEntity {

	@Id
	@Column(name = "cpoint_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer point;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ActivityType activityType;

	@Column(nullable = false)
	private Long memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@Builder
	public Cpoint(Long id, Integer point, ActivityType activityType, Long memberId, Company company,
		LocalDateTime createdAt) {
		this.id = id;
		this.point = point;
		this.activityType = activityType;
		this.memberId = memberId;
		this.company = company;
		this.createdAt = createdAt;
	}

	public static Cpoint of(CpointDto cpointDto, Company company) {
		return Cpoint.builder()
			.memberId(cpointDto.getMember().getMemberId())
			.activityType(cpointDto.getActivityType())
			.company(company)
			.point(cpointDto.getPoint())
			.build();

	}

	public static int getCpointAmountByActivityType(ActivityType activityType) {
		return switch (activityType) {
			case TUMBLER -> 300;
			case ELECTRONIC_RECEIPT -> 100;
			case DISPOSABLE_CUP -> 200;
			case DISCARDED_PHONE, ECO_FRIENDLY_PRODUCTS, MULTI_USE_CONTAINER -> 1000;
			case EMISSION_FREE_CAR -> 500;
			case HIGH_QUALITY_RECYCLED_PRODUCTS -> 300;
			case REFILL_STATION -> 2000;
			case ETC -> 0;
		};
	}

	public void minusMonthsCreatedAtForTest(Long months) {
		this.createdAt = this.createdAt.minusMonths(months);
	}

}
