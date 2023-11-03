package com.eokam.groo.acceptance;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.presentation.dto.GrooSavingRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrooAcceptanceTest {

	private static final SavingType SAVING_TYPE = SavingType.PROOF;
	private static final ActivityType ACTIVITY_TYPE = ActivityType.DISPOSABLE_CUP;
	private static final Long FK = 1L;
	private static final String ACCESS_TOKEN = "1";

	@LocalServerPort
	int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	@DisplayName("인증과 고발의 활동 타입에 따라 그루가 적립된다.")
	void createGrooSavings() {
		// when
		ExtractableResponse<Response> response = 그루_적립_요청함(ACCESS_TOKEN);

		// then
		그루_적립내역_생성됨(response);
	}

	private void 그루_적립내역_생성됨(ExtractableResponse<Response> response) {
		Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.header("Location")).isNotBlank();
	}

	private GrooSavingRequest 그루_적립_요청(SavingType savingType, ActivityType activityType,Long proofAccusationId, LocalDateTime savedAt){
		return GrooSavingRequest.builder()
			.savingType(savingType)
			.activityType(activityType)
			.proofAccusationId(proofAccusationId)
			.savedAt(savedAt)
			.build();
	}

	private ExtractableResponse<Response> 그루_적립_요청함(String accessToken) {
		GrooSavingRequest grooSavingRequest = 그루_적립_요청(SAVING_TYPE, ACTIVITY_TYPE, FK, LocalDateTime.now());

		return RestAssured
			.given().log().all()
			.cookie("access-token",accessToken)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(grooSavingRequest)
			.when().post("/groo")
			.then().log().all()
			.extract();
	}

}
