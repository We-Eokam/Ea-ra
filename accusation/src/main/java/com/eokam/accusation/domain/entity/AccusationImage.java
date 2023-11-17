package com.eokam.accusation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class AccusationImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Accusation accusation;

	@Column(nullable = false)
	private String fileUrl;

	public static AccusationImage of(Accusation accusation, String fileUrl) {
		return AccusationImage.builder()
			.accusation(accusation)
			.fileUrl(fileUrl)
			.build();
	}
}