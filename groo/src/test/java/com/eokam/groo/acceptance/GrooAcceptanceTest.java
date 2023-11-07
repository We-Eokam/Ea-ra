package com.eokam.groo.acceptance;

import static com.eokam.groo.acceptance.GrooAcceptanceStep.*;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.eokam.groo.utils.DatabaseCleanupExtension;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrooAcceptanceTest {

	private static final int FIRST_DAY_OF_WEEK = 5;
	private static final int END_DAY_OF_WEEK = 11;

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
		var 적립시간 = LocalDateTime.now();
		String body = 그루_적립_요청(인증, 텀블러_사용, 활동_ID, 적립시간);
		var response = 그루_적립_요청함(ACCESS_TOKEN, body);

		// then
		그루_적립내역_생성됨(response);
	}

	@Test
	@DisplayName("특정 월의 그린 적립 내역을 조회할 수 있다.")
	void getDailySavingAmountsByMonth() throws JsonProcessingException {
		// given
		그루_적립내역이_생성되어있음();

		// when
		var response = 월별_그린_적립내역_조회_요청(ACCESS_TOKEN, YEAR, MONTH);

		// then
		월별_그린_적립내역_응답됨(response);
		월별_그린_적립내역_조회됨(response);
	}

	@Test
	@DisplayName("일주일간(해당 주) 일별 인증 활동 수를 조회할 수 있다.")
	void getDailyProofCountByWeek() throws JsonProcessingException {
		// given
		Long 일주일간_인증_적립_수_총합 = 일주일간_인증_적립내역이_생성되어있음();

		// when
		var response = 일주일간_인증활동_수_조회_요청(ACCESS_TOKEN);

		// then
		일주일간_인증활동_수_응답됨(response);
		일주일간_인증활동_수_조회됨(response, 일주일간_인증_적립_수_총합);
	}

	private Long 일주일간_인증_적립내역이_생성되어있음() throws JsonProcessingException {
		long expectProofCount = 0;
		for (int i=0; i<20; i++){
			var 적립시간 = 랜덤_날짜_생성();
			var request = 그루_적립_요청(인증, 텀블러_사용, (long) i+10, 적립시간);
			var response = 그루_적립_요청함(ACCESS_TOKEN, request);
			if (response.statusCode()==HttpStatus.CREATED.value() && isDateInRange(적립시간)){
				expectProofCount++;
			}
		}
		return expectProofCount;
	}

	private LocalDateTime 랜덤_날짜_생성() {
		int randomNum = new Random().nextInt(10);
		LocalDateTime firstday = LocalDateTime.of(2023, 11, 5, 1, 0);
		return firstday.plusDays(randomNum);
	}

	private boolean isDateInRange(LocalDateTime localDateTime){
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

	public ExtractableResponse<Response> 일주일간_인증활동_수_조회_요청(String accessToken) {
		return given().log().all()
			.cookie("access-token", accessToken)
			.when().get("/groo/current-week")
			.then().log().all()
			.extract();
	}

	public void 일주일간_인증활동_수_조회됨(ExtractableResponse<Response> response, Long expectedProofCountSum) {
		List<Long> proofCountList = response.jsonPath().getList("groo_saving_list.proof_count", Long.class);
		long proofCountSum = proofCountList.stream()
			.mapToLong(Long::longValue)
			.sum();
		assertThat(proofCountSum).isEqualTo(expectedProofCountSum);
	}

	public void 일주일간_인증활동_수_응답됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

}
