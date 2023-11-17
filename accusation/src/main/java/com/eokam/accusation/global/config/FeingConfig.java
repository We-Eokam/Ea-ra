package com.eokam.accusation.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.eokam.accusation.global.error.FeignErrorDecoder;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "com.eokam")
@Import(FeignClientsConfiguration.class)
public class FeingConfig {
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}

	@Bean
	public Retryer retryer() {
		return new Retryer.Default(1000, 2000, 3);
	}
}
