package com.eokam.notification.infrastructure.fcm.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FcmConfig {
	@Value("${fcm.certification}")
	private String googleApplicationCredentials;

	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {
		return FirebaseMessaging.getInstance(firebaseApp());
	}

	@Bean
	public FirebaseApp firebaseApp() throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(googleCredentials()))
			.build();

		return FirebaseApp.initializeApp(options);
	}

	@Bean
	public InputStream googleCredentials() throws IOException {
		ClassPathResource resource = new ClassPathResource(googleApplicationCredentials);

		return resource.getInputStream();
	}
}
