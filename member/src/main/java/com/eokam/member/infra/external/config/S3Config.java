package com.eokam.member.infra.external.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;

@Configuration
public class S3Config {
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;

	@Bean
	public AmazonS3 amazonS3Client() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		return AmazonS3ClientBuilder
			.standard()
			.withRegion(String.valueOf(Region.AP_Seoul))
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
	}
}

