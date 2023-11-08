package com.eokam.groo.unit;

import static com.eokam.groo.unit.GrooSavingControllerTestUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;

import com.eokam.groo.presentation.dto.GrooSavingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.Cookie;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GrooSavingControllerTest {

	@Autowired
	public MockMvc mockMvc;

	public ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
	}

	@Test
	@DisplayName("savingType이 null인 경우")
	public void 적립내역_생성_savingType없음() throws Exception {
		GrooSavingRequest savingTypeNullDto = getSavingTypeNullDto();
		String content = objectMapper.writeValueAsString(savingTypeNullDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error_message").value("[savingType]must not be null"))
			.andReturn();
	}

	@Test
	@DisplayName("activityType이 null인 경우")
	public void 적립내역_생성_activityType없음() throws Exception {
		GrooSavingRequest activityTypeNullDto = getActivityTypeNullDto();
		String content = objectMapper.writeValueAsString(activityTypeNullDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error_message").value("[activityType]must not be null"))
			.andReturn();
	}

	@Test
	@DisplayName("proofAccusationId이 null인 경우")
	public void 적립내역_생성_proofAccusationId없음() throws Exception {
		GrooSavingRequest proofAccusationIdNullDto = getProofAccusationIdNullDto();
		String content = objectMapper.writeValueAsString(proofAccusationIdNullDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error_message").value("[proofAccusationId]must not be null"))
			.andReturn();
	}

	@Test
	@DisplayName("proofAccusationId이 음수인 경우")
	public void 적립내역_생성_proofAccusationId가_음수() throws Exception {
		GrooSavingRequest proofAccusationIdNegativeDto = getProofAccusationIdNegativeDto();
		String content = objectMapper.writeValueAsString(proofAccusationIdNegativeDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andReturn();
	}

	@Test
	@DisplayName("savedAt이 null인 경우")
	public void 적립내역_생성_savedAt없음() throws Exception {
		GrooSavingRequest savedAtNullDto = getSavedAtNullDto();
		String content = objectMapper.writeValueAsString(savedAtNullDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error_message").value("[savedAt]must not be null"))
			.andReturn();
	}

	@Test
	@DisplayName("savedAt이 미래인 경우")
	public void 적립내역_생성_savedAt_미래() throws Exception {
		GrooSavingRequest savedAtFutureDto = getSavedAtFutureDto();
		String content = objectMapper.writeValueAsString(savedAtFutureDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error_message").value("[savedAt]must be a past date"))
			.andReturn();
	}

	@Test
	@DisplayName("activityType의 savingType이 savingType과 일치하지 않는 경우")
	public void 적립내역_생성_savingType_불일치() throws Exception {
		GrooSavingRequest savingTypeNotMatchDto = getSavingTypeNotMatchDto();
		String content = objectMapper.writeValueAsString(savingTypeNotMatchDto);
		mockMvc
			.perform(
				post("/groo")
					.cookie(new Cookie("access-token", ACCESS_TOKEN))
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
					.header("Accept-Language", "en-US"))
			.andExpect(
				(result) -> Assertions.assertTrue(result.getResolvedException() instanceof BindException))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error_message").value("[activityType]활동 타입이 인증 또는 고발 타입과 일치하지 않습니다."))
			.andReturn();
	}


}
