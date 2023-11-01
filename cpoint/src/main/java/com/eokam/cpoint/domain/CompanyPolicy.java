package com.eokam.cpoint.domain;

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
public class CompanyPolicy extends BaseEntity {

	@Id
	@Column(name = "company_policy_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ActivityType activityType;

	@Column(nullable = false)
	private String target;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Builder
	public CompanyPolicy(Long id, ActivityType activityType, String target, Company company) {
		this.id = id;
		this.activityType = activityType;
		this.target = target;
		this.company = company;
	}
}
