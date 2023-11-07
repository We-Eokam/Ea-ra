package com.eokam.groo.acceptance;

import static com.eokam.groo.acceptance.GrooAcceptanceStep.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.eokam.groo.utils.DatabaseCleanupExtension;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
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

}
