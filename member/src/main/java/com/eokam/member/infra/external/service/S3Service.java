package com.eokam.member.infra.external.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.AmazonS3Exception;
import com.eokam.member.infra.external.S3FileDetail;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public S3FileDetail save(MultipartFile multipartFile) {
		String originalFileName =
			LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + UUID.randomUUID();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

		try {
			amazonS3.putObject(bucket, originalFileName, multipartFile.getInputStream(), metadata);
		} catch (IOException e) {
			throw new AmazonS3Exception(ErrorCode.S3_NOT_UPLOAD);
		}

		String savedUrl = amazonS3.getUrl(bucket, originalFileName).toString();

		return new S3FileDetail(originalFileName, savedUrl);
	}
}

