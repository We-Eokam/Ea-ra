package com.eokam.member.infra.external.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.LoginException;
import com.eokam.member.infra.dto.KakaoAccessTokenResponse;
import com.eokam.member.infra.dto.KakaoMemberResponse;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
public class OauthProvider {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String GRANT_TYPE = "authorization_code";
	public static final String TOKEN_TYPE = "Bearer ";

	@Value("${auth.kakao.token-request-uri}")
	private String tokenRequestUri;

	@Value("${auth.kakao.member-info-request-uri}")
	private String memberInfoRequestUri;

	@Value("${auth.kakao.client-id}")
	private String clientId;

	@Value("${auth.kakao.admin-id}")
	private String adminId;

	@Value("${auth.kakao.redirect-uri}")
	private String redirectUri;

	@Value("${auth.kakao.unlink-uri}")
	private String unLinkUri;

	private final RestTemplate restTemplate;

	public OauthProvider(@Autowired RestTemplateBuilder restTemplateBuilder){
		this.restTemplate = restTemplateBuilder.build();
	}


	public KakaoMemberResponse getMemberInfo(String accessToken) {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken);
			httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<Object> request = new HttpEntity<>(httpHeaders);

			return restTemplate.postForEntity(memberInfoRequestUri, request, KakaoMemberResponse.class)
				.getBody();
		} catch (HttpClientErrorException e) {
			throw new LoginException(ErrorCode.KAKAO_TOKEN_REQUEST_FAIL);
		} catch (HttpServerErrorException e) {
			throw new LoginException(ErrorCode.KAKAO_SERVER_ERROR);
		}
	}

	public KakaoAccessTokenResponse getAccessToken(String authorizationCode) {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
			body.add("grant_type", GRANT_TYPE);
			body.add("client_id", clientId);
			body.add("redirect_uri", redirectUri);
			body.add("code", authorizationCode);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

			return restTemplate.postForEntity(
				tokenRequestUri, request, KakaoAccessTokenResponse.class).getBody();
		} catch (HttpClientErrorException e) {
			throw new LoginException(ErrorCode.KAKAO_TOKEN_REQUEST_FAIL);
		} catch (HttpServerErrorException e) {
			throw new LoginException(ErrorCode.KAKAO_SERVER_ERROR);
		}
	}
}