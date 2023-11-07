package com.eokam.groo.acceptance;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Random;

import org.springframework.http.HttpStatus;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.presentation.dto.GrooSavingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class GrooAcceptanceStep {
	public static final SavingType 인증 = SavingType.PROOF;
	public static final SavingType 제보 = SavingType.ACCUSATION;
	public static final ActivityType 텀블러_사용 = ActivityType.TUMBLER;
	public static final ActivityType 일회용컵_사용 = ActivityType.DISPOSABLE;
	public static final Long 활동_ID = 1L;
	public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6MX0.b9AyXTApiN9ii7WMT1GO8h_wjWgGG5hsW11hXT3RXXk";
	public static final Integer YEAR = 2023;
	public static final Integer MONTH = 11;
	private static Long expectedAccusationSum;
	private static Long expectedAccusationCount;
	private static Long expectedProofSum;
	private static Long expectProofCount;

	public static void 월별_그린_적립내역_조회됨(ExtractableResponse<Response> response) {
		long 이번달_얻은_그루_합 = response.jsonPath().getLong("proof_sum");
		long 이번달_인증_활동_횟수 = response.jsonPath().getLong("proof_count");
		long 이번달_받은_제보_그루_합 = response.jsonPath().getLong("accusation_sum");
		long 이번달_받은_제보_횟수 = response.jsonPath().getLong("accusation_count");

		assertThat(이번달_얻은_그루_합).isEqualTo(expectedProofSum);
		assertThat(이번달_인증_활동_횟수).isEqualTo(expectProofCount);
		assertThat(이번달_받은_제보_그루_합).isEqualTo(expectedAccusationSum);
		assertThat(이번달_받은_제보_횟수).isEqualTo(expectedAccusationCount);
	}

	public static void 월별_그린_적립내역_응답됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	public static ExtractableResponse<Response> 월별_그린_적립내역_조회_요청(String accessToken, int year, int month) {
		return given().log().all()
			.cookie("access-token", accessToken)
			.queryParam("year", year)
			.queryParam("month", month)
			.when().get("/groo")
			.then().log().all()
			.extract();
	}

	public static void 그루_적립내역이_생성되어있음() throws JsonProcessingException {
		횟수_적립양_초기화();
		랜덤_날짜_인증_활동_20개_생성(인증, 텀블러_사용);
		랜덤_날짜_인증_활동_20개_생성(제보, 일회용컵_사용);
	}

	private static void 횟수_적립양_초기화() {
		expectedAccusationSum = 0L;
		expectedAccusationCount = 0L;
		expectedProofSum = 0L;
		expectProofCount = 0L;
	}

	public static void 그루_적립내역_생성됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.header("Location")).isNotBlank();
	}

	public static String 그루_적립_요청(SavingType savingType, ActivityType activityType,Long proofAccusationId, LocalDateTime savedAt) throws
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

	public static ExtractableResponse<Response> 그루_적립_요청함(String accessToken, String request) throws JsonProcessingException {
		return given().log().all()
			.cookie("access-token", accessToken)
			.contentType(ContentType.JSON)
			.body(request)
			.when().post("/groo")
			.then().log().all()
			.extract();
	}

	private static void 랜덤_날짜_인증_활동_20개_생성(SavingType savingType, ActivityType activityType) throws JsonProcessingException {
		for (int i=0; i<20; i++){
			int randomNum = new Random().nextInt(50);
			var 적립시간 = LocalDateTime.now().plusDays(randomNum);
			String request = 그루_적립_요청(savingType, activityType, (long) i+10, 적립시간);
			var response = 그루_적립_요청함(ACCESS_TOKEN, request);
			if (response.statusCode()==HttpStatus.CREATED.value() && 적립시간.getMonth().getValue() == MONTH){
				if (SavingType.PROOF.equals(savingType)) {
					expectedProofSum += activityType.getSavingAmount();
					expectProofCount++;
				} else {
					expectedAccusationSum += activityType.getSavingAmount();
					expectedAccusationCount++;
				}
			}
		}
	}
}
