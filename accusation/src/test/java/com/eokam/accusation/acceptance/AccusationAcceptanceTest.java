package com.eokam.accusation.acceptance;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.presentation.dto.AccusationRequest;
import com.eokam.accusation.utils.DatabaseCleanupExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccusationAcceptanceTest {

	private static final long WITNESS_ID = 1L;
	private static final long MEMBER_ID = 2L;
	private static final ActivityType ACTIVITY_TYPE = ActivityType.PLASTIC;

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
	@Disabled
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

	public static ExtractableResponse<Response> 고발장_생성되어_있음(String fileName) throws IOException {
		return 고발장_생성_요청(고발장_요청_JSON(), 고발장_요청_사진(fileName));
	}

	public static ExtractableResponse<Response> 받은_고발장_목록_조회_요청() throws IOException {
		return given().log().all()
			.header("Authorization", MEMBER_ID)
			.when().get("/accusation")
			.then().log().all()
			.extract();
	}

	public static String 고발장_요청_JSON() throws JsonProcessingException {
		AccusationRequest accusationRequest = AccusationRequest.builder()
			.witnessId(WITNESS_ID)
			.memberId(MEMBER_ID)
			.activityType(ACTIVITY_TYPE)
			.build();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		return objectMapper.writeValueAsString(accusationRequest);
	}

	public static MockMultipartFile 고발장_요청_사진(String fileName) {
		return new MockMultipartFile("file", fileName, "image/png", fileName.getBytes());
	}

	public static void 고발장_생성됨(ExtractableResponse<Response> response) {
		Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.header("Location")).isNotBlank();
	}

	public static ExtractableResponse<Response> 고발장_생성_요청(String content, MockMultipartFile file) throws IOException {
		return given().log().all()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.multiPart("file", file.getOriginalFilename(), file.getBytes(),
				file.getContentType())
			.multiPart("content", content, "application/json")
			.when().post("/accusation")
			.then().log().all()
			.extract();
	}

	public static void 받은_고발장_목록_응답됨(ExtractableResponse<Response> response) {
		Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	public static void 받은_고발장_목록_포함됨(ExtractableResponse<Response> response,
		List<ExtractableResponse<Response>> createdResponses) {

		List<Long> expectedAccusationIds = createdResponses.stream()
			.map(r -> Long.parseLong(r.header("Location").split("/")[2]))
			.toList();

		List<Long> resultAccusationIds = response.jsonPath().getList("accusation_list.accusation_id");

		Assertions.assertThat(resultAccusationIds).containsAll(expectedAccusationIds);
	}

}
