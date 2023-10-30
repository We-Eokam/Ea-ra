package com.eokam.proof.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProofImage {
	@Id
	@GeneratedValue
	Long proofImageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proofId")
	Proof proof;

	@Column(nullable = false)
	String fileName;

	@Column(nullable = false)
	String fileUrl;
}
