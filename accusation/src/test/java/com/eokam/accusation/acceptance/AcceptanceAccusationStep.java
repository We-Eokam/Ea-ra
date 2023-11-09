package com.eokam.accusation.acceptance;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.presentation.dto.AccusationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AcceptanceAccusationStep {

	private static final long WITNESS_ID = 1L;
	private static final long MEMBER_ID = 2L;
	private static final ActivityType ACTIVITY_TYPE = ActivityType.PLASTIC;

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

	public static ExtractableResponse<Response> 고발장_생성되어_있음(String fileName) throws IOException {
		return 고발장_생성_요청(고발장_요청_JSON(), 고발장_요청_사진(fileName));
	}

	public static ExtractableResponse<Response> 받은_고발장_목록_조회_요청() throws IOException {
		return given().log().all()
			.queryParam("memberId", MEMBER_ID)
			.queryParam("page", 0)
			.queryParam("size", 12)
			.when().get("/accusation")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 받은_고발장_목록_상세_요청(Long accusationId) {
		return given().log().all()
			.pathParam("accusationId", accusationId)
			.when().get("/accusation/{accusationId}")
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
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.header("Location")).isNotBlank();
	}

	public static void 받은_고발장_목록_응답됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	public static void 받은_고발장_목록_포함됨(ExtractableResponse<Response> response,
		List<ExtractableResponse<Response>> createdResponses) {

		List<Long> expectedAccusationIds = createdResponses.stream()
			.map(r -> Long.valueOf(r.header("Location").split("/")[2]))
			.toList();

		List<Long> resultAccusationIds = response.jsonPath().getList("accusation_list.accusation_id", Long.class);

		assertThat(resultAccusationIds).containsAll(expectedAccusationIds);
	}

	public static void 받은_고발장_상세조회_응답됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	public static void 받은_고발장_상세조회_포함됨(ExtractableResponse<Response> response,
		ExtractableResponse<Response> accusation) {
		long accusationId = response.jsonPath().getLong("accusation_id");
		long expectedAccusationId = accusation.jsonPath().getLong("accusation_id");
		assertThat(accusationId).isEqualTo(expectedAccusationId);
	}

}
