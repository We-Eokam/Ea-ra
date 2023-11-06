package com.eokam.cpoint.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Store extends BaseEntity {

	@Id
	@Column(name = "store_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	String branch;

	@Column(nullable = false)
	Double latitude;

	@Column(nullable = false)
	Double longitude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	Company company;

	@Builder
	public Store(Long id, String branch, Double latitude, Double longitude, Company company) {
		this.id = id;
		this.branch = branch;
		this.latitude = latitude;
		this.longitude = longitude;
		this.company = company;
	}
}
