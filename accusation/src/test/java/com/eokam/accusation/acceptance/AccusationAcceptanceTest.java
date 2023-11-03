package com.eokam.accusation.acceptance;

import static com.eokam.accusation.acceptance.AcceptanceAccusationStep.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import com.eokam.accusation.utils.DatabaseCleanupExtension;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccusationAcceptanceTest {

	private static final String FILE_NAME1 = "fileName1.png";
	private static final String FILE_NAME2 = "fileName2.png";

	@LocalServerPort
	int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	@DisplayName("고발장을 갖고 있는 회원은 친구에게 고발장을 보낼 수 있다.")
	void createAccusation() throws IOException {
		// when
		ExtractableResponse<Response> response = 고발장_생성_요청(고발장_요청_JSON(), 고발장_요청_사진(FILE_NAME1));

		// then
		고발장_생성됨(response);
	}

	@Test
	@DisplayName("회원은 자신이 받은 고발장 목록을 조회할 수 있다.")
	void getMyAccusations() throws IOException {
		// given
		ExtractableResponse<Response> accusation1 = 고발장_생성되어_있음(FILE_NAME1);
		ExtractableResponse<Response> accusation2 = 고발장_생성되어_있음(FILE_NAME2);

		// when
		ExtractableResponse<Response> response = 받은_고발장_목록_조회_요청();

		// then
		받은_고발장_목록_응답됨(response);
		받은_고발장_목록_포함됨(response, Arrays.asList(accusation1, accusation2));
	}

	@Test
	@DisplayName("회원은 자신이 받은 고발장 하나를 상세 조회할 수 있다.")
	void getMyAccusationDetail() throws IOException {
		// given
		ExtractableResponse<Response> accusation = 고발장_생성되어_있음(FILE_NAME1);
		ExtractableResponse<Response> accusationList = 받은_고발장_목록_조회_요청();
		List<Long> resultAccusationId = accusationList.jsonPath().getList("accusation_list.accusation_id", Long.class);

		// when
		ExtractableResponse<Response> response = 받은_고발장_목록_상세_요청(resultAccusationId.get(0));

		// then
		받은_고발장_상세조회_응답됨(response);
		받은_고발장_상세조회_포함됨(response, accusation);
	}

}
