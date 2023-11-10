package com.eokam.groo.acceptance;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private static final int FIRST_DAY_OF_WEEK = 5;
	private static final int END_DAY_OF_WEEK = 11;

	public static void 월별_그린_적립내역_조회됨(ExtractableResponse<Response> response, Map<String, Long> map) {
		long 이번달_얻은_그루_합 = response.jsonPath().getLong("proof_sum");
		long 이번달_인증_활동_횟수 = response.jsonPath().getLong("proof_count");
		long 이번달_받은_제보_그루_합 = response.jsonPath().getLong("accusation_sum");
		long 이번달_받은_제보_횟수 = response.jsonPath().getLong("accusation_count");

		assertThat(이번달_얻은_그루_합).isEqualTo(map.get("expectedProofSum"));
		assertThat(이번달_인증_활동_횟수).isEqualTo(map.get("expectedProofCount"));
		assertThat(이번달_받은_제보_그루_합).isEqualTo(map.get("expectedAccusationSum"));
		assertThat(이번달_받은_제보_횟수).isEqualTo(map.get("expectedAccusationCount"));
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

	public static Map<String, Long> 그루_적립내역이_생성되어있음() throws JsonProcessingException {
		Map<String, Long> sumAndCount = 횟수_적립양_초기화();
		랜덤_날짜_인증_활동_20개_생성(인증, 텀블러_사용, sumAndCount);
		랜덤_날짜_인증_활동_20개_생성(제보, 일회용컵_사용, sumAndCount);
		return sumAndCount;
	}

	private static Map<String, Long> 횟수_적립양_초기화() {
		Map<String, Long> map = new HashMap<>();
		map.put("expectedAccusationSum", 0L);
		map.put("expectedAccusationCount", 0L);
		map.put("expectedProofSum", 0L);
		map.put("expectProofCount", 0L);
		return map;
	}

	public static void 그루_적립내역_생성됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.header("Location")).isNotBlank();
	}

	public static String 그루_적립_요청(Long memberId, SavingType savingType, ActivityType activityType,Long proofAccusationId, LocalDateTime savedAt) throws
		JsonProcessingException {
		GrooSavingRequest grooSavingRequest = GrooSavingRequest.builder()
			.memberId(memberId)
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

	public static ExtractableResponse<Response> 그루_적립_요청함(String accessToken, String request) {
		return given().log().all()
			.cookie("access-token", accessToken)
			.contentType(ContentType.JSON)
			.body(request)
			.when().post("/groo")
			.then().log().all()
			.extract();
	}

	private static Map<String, Long> 랜덤_날짜_인증_활동_20개_생성(SavingType savingType, ActivityType activityType, Map<String, Long> sumAndCount) throws JsonProcessingException {
		for (int i=0; i<20; i++){
			int randomNum = new Random().nextInt(50);
			var 적립시간 = LocalDateTime.of(2023,11,1,1,0).plusDays(randomNum);
			String request = 그루_적립_요청(1L, savingType, activityType, (long) i+10, 적립시간);
			var response = 그루_적립_요청함(ACCESS_TOKEN, request);
			if (response.statusCode()==HttpStatus.CREATED.value() && 적립시간.getYear() == YEAR && 적립시간.getMonth().getValue() == MONTH){
				if (SavingType.PROOF.equals(savingType)) {
					sumAndCount.put("expectedProofSum", sumAndCount.getOrDefault("expectedProofSum", 0L) + activityType.getSavingAmount());
					sumAndCount.put("expectedProofCount", sumAndCount.getOrDefault("expectedProofCount", 0L) + 1L);
				} else {
					sumAndCount.put("expectedAccusationSum", sumAndCount.getOrDefault("expectedAccusationSum", 0L) + activityType.getSavingAmount());
					sumAndCount.put("expectedAccusationCount", sumAndCount.getOrDefault("expectedAccusationCount", 0L) + 1L);
				}
			}
		}
		return sumAndCount;
	}

	public static Long 일주일간_인증_적립내역이_생성되어있음() throws JsonProcessingException {
		long expectProofCount = 0;
		for (int i=0; i<20; i++){
			var 적립시간 = 랜덤_날짜_생성();
			var request = 그루_적립_요청(1L, 인증, 텀블러_사용, (long) i+10, 적립시간);
			var response = 그루_적립_요청함(ACCESS_TOKEN, request);
			if (response.statusCode()==HttpStatus.CREATED.value() && isDateInRange(적립시간)){
				expectProofCount++;
			}
		}
		return expectProofCount;
	}

	private static LocalDateTime 랜덤_날짜_생성() {
		int randomNum = new Random().nextInt(10);
		LocalDateTime firstday = LocalDateTime.of(2023, 11, 5, 1, 0);
		return firstday.plusDays(randomNum);
	}

	private static boolean isDateInRange(LocalDateTime localDateTime){
		if (localDateTime.getYear() != YEAR){
			return false;
		}
		if (localDateTime.getMonthValue() != MONTH) {
			return false;
		}
		if (localDateTime.getDayOfMonth() < FIRST_DAY_OF_WEEK || localDateTime.getDayOfMonth() > END_DAY_OF_WEEK){
			return false;
		}
		return true;
	}

	public static ExtractableResponse<Response> 일주일간_인증활동_수_조회_요청(String accessToken) {
		return given().log().all()
			.cookie("access-token", accessToken)
			.when().get("/groo/current-week")
			.then().log().all()
			.extract();
	}

	public static void 일주일간_인증활동_수_조회됨(ExtractableResponse<Response> response, Long expectedProofCountSum) {
		List<Long> proofCountList = response.jsonPath().getList("groo_saving_list.proof_count", Long.class);
		long proofCountSum = proofCountList.stream()
			.mapToLong(Long::longValue)
			.sum();
		assertThat(proofCountSum).isEqualTo(expectedProofCountSum);
	}

	public static void 일주일간_인증활동_수_응답됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
