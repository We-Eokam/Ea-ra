package com.eokam.proof.infrastructure.external.s3;

import lombok.Builder;

@Builder
public record S3FileDetail(String originalFileName, String url) {
	public static S3FileDetail of(String savedFileName, String savedFileUrl) {
		return S3FileDetail.builder()
			.originalFileName(savedFileName)
			.url(savedFileUrl)
			.build();
	}
}
