package com.eokam.accusation.acceptance;

import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.presentation.dto.AccusationRequest;
import com.eokam.accusation.utils.DatabaseCleanupExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ExtendWith(DatabaseCleanupExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccusationAcceptanceTest {

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
		ExtractableResponse<Response> response = 고발장_생성_요청();

		// then
		고발장_생성됨(response);
	}

	public static void 고발장_생성됨(ExtractableResponse<Response> response) {
		Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.header("Location")).isNotBlank();
	}

	public static ExtractableResponse<Response> 고발장_생성_요청() throws IOException {
		AccusationRequest accusationRequest = AccusationRequest.builder()
			.witnessId(1L)
			.memberId(2L)
			.activityType(ActivityType.WATER)
			.build();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		String jsonString = objectMapper.writeValueAsString(accusationRequest);

		URL resource = AccusationAcceptanceTest.class.getClassLoader().getResource("earth.png");
		assert resource != null;
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "earth.png", "image/png",
			new FileInputStream(ResourceUtils.getFile(resource)));

		return given().log().all()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.multiPart("file", mockMultipartFile.getOriginalFilename(), mockMultipartFile.getBytes(),
				mockMultipartFile.getContentType())
			.multiPart("content", jsonString, "application/json")
			.when().post("/accusation")
			.then().log().all()
			.extract();
	}

}
