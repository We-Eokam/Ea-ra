package com.eokam.accusation.acceptance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.presentation.dto.AccusationCreateRequest;
import com.eokam.accusation.utils.DatabaseCleanupExtension;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccusationAcceptanceTest {

	@LocalServerPort
	int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	@DisplayName("고발장을 갖고 있는 회원은 친구에게 고발장을 보낼 수 있다.")
	void createAccusation() {
		// when
		ExtractableResponse<Response> response = 고발장_생성_요청();

		// then
		고발장_생성됨(response);
	}

	public static void 고발장_생성됨(ExtractableResponse<Response> response) {
		Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	public static ExtractableResponse<Response> 고발장_생성_요청() {
		AccusationCreateRequest accusationRequest = AccusationCreateRequest.builder()
			.witnessId(1L)
			.memberId(2L)
			.activityType(ActivityType.WATER)
			.build();

		return RestAssured
			.given().log().all()
			.body(accusationRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/accusation")
			.then().log().all()
			.extract();
	}

}
