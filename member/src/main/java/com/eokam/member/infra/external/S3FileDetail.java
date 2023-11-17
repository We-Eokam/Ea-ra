package com.eokam.member.infra.external;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class S3FileDetail{

	private String fileName;

	private String url;

	public S3FileDetail(String fileName, String url) {
		this.fileName = fileName;
		this.url = url;
	}
}

