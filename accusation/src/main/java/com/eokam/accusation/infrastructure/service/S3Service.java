package com.eokam.accusation.infrastructure.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Service {

	private final AmazonS3 amazonS3Client;

	@Value("${aws.s3.bucket}")
	private String bucket;

	public List<String> uploadFile(List<MultipartFile> multipartFiles) throws IOException {
		List<String> fileUrls = new ArrayList<>();
		for (MultipartFile file : multipartFiles) {
			String fileName = makeFileName(file.getOriginalFilename());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(file.getContentType());
			objectMetadata.setContentLength(file.getSize());
			amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
			fileUrls.add(amazonS3Client.getUrl(bucket, fileName).toString());
		}
		return fileUrls;
	}

	public String makeFileName(String originalFileName) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mmssSSS")));
		fileName.append("_").append(originalFileName);
		return fileName.toString();
	}

}
