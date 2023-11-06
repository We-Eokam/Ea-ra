package com.eokam.groo.acceptance;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.presentation.dto.GrooSavingRequest;
import com.eokam.groo.utils.DatabaseCleanupExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrooAcceptanceTest {

	private static final SavingType SAVING_TYPE = SavingType.PROOF;
	private static final ActivityType ACTIVITY_TYPE = ActivityType.DISPOSABLE_CUP;
	private static final Long FK = 1L;
	private static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6MX0.b9AyXTApiN9ii7WMT1GO8h_wjWgGG5hsW11hXT3RXXk";
	private static final Integer YEAR = 2023;
	private static final Integer MONTH = 11;

	@LocalServerPort
	int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	@DisplayName("인증과 고발의 활동 타입에 따라 그루가 적립된다.")
	void createGrooSavings() throws JsonProcessingException {
		// when
		String request = 그루_적립_요청(SAVING_TYPE, ACTIVITY_TYPE, FK, LocalDateTime.now());
		ExtractableResponse<Response> response = 그루_적립_요청함(ACCESS_TOKEN, request);

		// then
		그루_적립내역_생성됨(response);
	}

	@Test
	@DisplayName("특정 월의 그린 적립 내역을 조회할 수 있다.")
	void getDailySavingAmountsByMonth() throws JsonProcessingException {
		// given
		HashMap<String, Long> hashMap = 그루_적립내역이_생성되어있음();

		// when
		ExtractableResponse<Response> response = 월별_그린_적립내역_조회_요청();

		// then
		월별_그린_적립내역_응답됨(response);
		월별_그린_적립내역_조회됨(response, hashMap);
	}

	private void 월별_그린_적립내역_조회됨(ExtractableResponse<Response> response, HashMap<String, Long> hashMap) {
		long proofSum = response.jsonPath().getLong("proof_sum");
		long proofCount = response.jsonPath().getLong("proof_count");
		long accusationSum = response.jsonPath().getLong("accusation_sum");
		long accusationCount = response.jsonPath().getLong("accusation_count");

		assertThat(proofSum).isEqualTo(hashMap.get("proofSum"));
		assertThat(proofCount).isEqualTo(hashMap.get("proofCount"));
		assertThat(accusationSum).isEqualTo(hashMap.get("accusationSum"));
		assertThat(accusationCount).isEqualTo(hashMap.get("accusationCount"));
	}

	private void 월별_그린_적립내역_응답됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	private ExtractableResponse<Response> 월별_그린_적립내역_조회_요청() {
		return given().log().all()
			.queryParam("year", YEAR)
			.queryParam("month", MONTH)
			.when().get("/groo")
			.then().log().all()
			.extract();
	}

	private HashMap<String, Long> 그루_적립내역이_생성되어있음() throws JsonProcessingException {
		long proofSum = 0;
		long proofCount = 0;
		long accusationSum = 0;
		long accusationCount = 0;

		HashMap<String, Long> hashMap = new HashMap<>();

		for (int i=0; i<10; i++){
			LocalDateTime localDateTime = LocalDateTime.now().plusDays(i + 10);
			String request = 그루_적립_요청(SavingType.PROOF, ActivityType.DISPOSABLE, (long) i+10, localDateTime);
			ExtractableResponse<Response> response = 그루_적립_요청함(ACCESS_TOKEN, request);
			if (response.statusCode()==HttpStatus.CREATED.value() && localDateTime.getMonth().equals(Month.NOVEMBER)){
				proofSum += ActivityType.DISPOSABLE.getSavingAmount();
				proofCount++;
			}
		}
		for (int i=0; i<20; i++){
			LocalDateTime localDateTime = LocalDateTime.now().plusDays(i + 14);
			String request = 그루_적립_요청(SavingType.ACCUSATION, ActivityType.FOOD, (long) i+10, localDateTime);
			ExtractableResponse<Response> response = 그루_적립_요청함(ACCESS_TOKEN, request);
			if (response.statusCode()==HttpStatus.CREATED.value() && localDateTime.getMonth().equals(Month.NOVEMBER)){
				accusationSum += ActivityType.FOOD.getSavingAmount();
				accusationCount++;
			}
		}

		hashMap.put("proofSum", proofSum);
		hashMap.put("proofCount", proofCount);
		hashMap.put("accusationSum", accusationSum);
		hashMap.put("accusationCount", accusationCount);

		return hashMap;
	}

	private void 그루_적립내역_생성됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.header("Location")).isNotBlank();
	}

	private String 그루_적립_요청(SavingType savingType, ActivityType activityType,Long proofAccusationId, LocalDateTime savedAt) throws
		JsonProcessingException {
		GrooSavingRequest grooSavingRequest = GrooSavingRequest.builder()
			.savingType(savingType)
			.activityType(activityType)
			.proofAccusationId(proofAccusationId)
			.savedAt(savedAt)
			.build();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		return objectMapper.writeValueAsString(grooSavingRequest);
	}

	private ExtractableResponse<Response> 그루_적립_요청함(String accessToken, String request) throws JsonProcessingException {
		return given().log().all()
			.cookie("access-token", accessToken)
			.contentType(ContentType.JSON)
			.body(request)
			.when().post("/groo")
			.then().log().all()
			.extract();
	}

}
