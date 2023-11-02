package com.eokam.proof.domain.entity;

import com.eokam.proof.infrastructure.external.s3.S3FileDetail;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "proof_id", nullable = false)
	Proof proof;

	@Column(nullable = false)
	String fileName;

	@Column(nullable = false)
	String fileUrl;

	public static ProofImage of(S3FileDetail fileDetail, Proof proof) {
		return ProofImage.builder()
			.fileName(fileDetail.originalFileName())
			.fileUrl(fileDetail.url())
			.proof(proof)
			.build();
	}
}
