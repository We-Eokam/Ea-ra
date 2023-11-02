package com.eokam.proof.infrastructure.external.s3.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.eokam.proof.infrastructure.external.s3.S3FileDetail;
import com.eokam.proof.infrastructure.util.error.ErrorCode;
import com.eokam.proof.infrastructure.util.error.exception.AmazonS3Exception;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public List<S3FileDetail> saveList(List<MultipartFile> multipartFile) {
		return multipartFile.stream()
			.map(this::save)
			.toList();
	}

	public S3FileDetail save(MultipartFile multipartFile) {
		String originalFileName = multipartFile.getOriginalFilename();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

		try {
			amazonS3.putObject(bucket, originalFileName, multipartFile.getInputStream(), metadata);
		} catch (IOException e) {
			throw new AmazonS3Exception(ErrorCode.S3_STORE_ERROR);
		}

		String savedUrl = amazonS3.getUrl(bucket, originalFileName).toString();

		return S3FileDetail.of(originalFileName, savedUrl);
	}
}
