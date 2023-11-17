package com.eokam.groo.acceptance;

import static com.eokam.groo.acceptance.GrooAcceptanceStep.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import com.eokam.groo.utils.DatabaseCleanupExtension;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.RestAssured;

@ExtendWith(DatabaseCleanupExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrooAcceptanceTest {

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
		String body = 그루_적립_요청(1L, 인증, 텀블러_사용, 활동_ID, 적립시간);
		var response = 그루_적립_요청함(ACCESS_TOKEN, body);

		// then
		그루_적립내역_생성됨(response);
	}

	@Test
	@DisplayName("특정 월의 그린 적립 내역을 조회할 수 있다.")
	void getDailySavingAmountsByMonth() throws JsonProcessingException {
		// given
		var 한달간_활동별_그루_적립_양_횟수 = 그루_적립내역이_생성되어있음();

		// when
		var response = 월별_그린_적립내역_조회_요청(ACCESS_TOKEN, YEAR, MONTH);

		// then
		월별_그린_적립내역_응답됨(response);
		월별_그린_적립내역_조회됨(response, 한달간_활동별_그루_적립_양_횟수);
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

}
