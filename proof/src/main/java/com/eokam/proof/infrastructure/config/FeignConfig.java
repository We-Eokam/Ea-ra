package com.eokam.proof.infrastructure.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@Configuration
@EnableFeignClients
public class FeignConfig {
	@Bean
	Encoder formEncoder(ObjectFactory<HttpMessageConverters> converters) {
		return new SpringFormEncoder(new SpringEncoder(converters));
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
