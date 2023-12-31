package com.eokam.cpoint.acceptance.common;

import static org.assertj.core.api.Assertions.*;

import java.util.Base64;

import org.springframework.http.HttpStatus;

import com.eokam.cpoint.presentation.dto.MemberDto;
import com.eokam.cpoint.presentation.dto.MemberRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CommonSteps {
	public static HttpStatus 정상생성 = HttpStatus.CREATED;
	public static HttpStatus 정상조회 = HttpStatus.OK;

	public static void HTTP_상태코드를_검증한다(final ExtractableResponse<Response> 응답, final HttpStatus HTTP_상태코드) {
		assertThat(응답.statusCode()).isEqualTo(HTTP_상태코드.value());
	}

	public static String JWT_쿠키_생성(final Long memberId) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String JWT_쿠키 = Base64.getUrlEncoder().encodeToString(
				objectMapper.writeValueAsString(
					MemberDto.builder()
						.memberId(memberId)
						.memberRole(MemberRole.MEMBER)
						.build()
				).getBytes()
			);
			return JWT_쿠키;
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
