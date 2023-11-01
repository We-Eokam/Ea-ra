package com.eokam.proof.presentation.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.application.dto.ProofImageDto;
import com.eokam.proof.application.service.ProofService;
import com.eokam.proof.common.BaseControllerTest;
import com.eokam.proof.domain.constant.ActivityType;

import jakarta.servlet.http.Cookie;

class ProofControllerTest extends BaseControllerTest {

	@InjectMocks
	ProofController proofController;

	@Mock
	ProofService proofService;

	private static final List<ProofDto> EXPECTED_MY_PROOF_LIST = new ArrayList<>();

	@BeforeEach
	public void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(proofController).build();
	}

	@Test
	@DisplayName("내 인증 목록 조회 성공")
	@Transactional
	void getMyProofList_Success() throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

		PageRequest pageRequest = PageRequest.of(0, 5);

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), EXPECTED_MY_PROOF_LIST.size());

		Page<ProofDto> proofPage = new PageImpl<>(EXPECTED_MY_PROOF_LIST.subList(start, end), pageRequest,
			EXPECTED_MY_PROOF_LIST.size());

		given(proofService.getMyProofList(anyString(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when & then
		this.mockMvc.perform(get("/proof/me")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("proof")
				.isArray()
			)
			.andExpect(jsonPath("proof[0].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).proofId())
			)
			.andExpect(jsonPath("proof[0].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(0).activityType().name())
			)
			.andExpect(jsonPath("proof[0].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).cCompanyId())
			)
			.andExpect(jsonPath("proof[0].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(0)
					.createdAt()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(
				jsonPath("proof[0].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[0].picture[0].url")
					.value("http://test1.com")
			)
			.andExpect(
				jsonPath("proof[0].picture[0].name")
					.value("test1.jpg")
			)
			.andExpect(jsonPath("proof[0].content")
				.doesNotExist()
			)
			.andExpect(jsonPath("proof[1].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).proofId())
			)
			.andExpect(jsonPath("proof[1].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(1).activityType().name())
			)
			.andExpect(jsonPath("proof[1].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).cCompanyId())
			)
			.andExpect(jsonPath("proof[1].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(1)
					.createdAt()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(
				jsonPath("proof[1].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[1].picture[0].url")
					.value("http://test2.com")
			)
			.andExpect(
				jsonPath("proof[1].picture[0].name")
					.value("test2.jpg")
			)
			.andExpect(jsonPath("proof[1].content")
				.doesNotExist()
			)
			.andExpect(jsonPath("proof[2].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).proofId())
			)
			.andExpect(jsonPath("proof[2].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(2).activityType().name())
			)
			.andExpect(jsonPath("proof[2].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).cCompanyId())
			)
			.andExpect(jsonPath("proof[2].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(2)
					.createdAt()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(
				jsonPath("proof[2].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[2].picture[0].url")
					.value("http://test3.com")
			)
			.andExpect(
				jsonPath("proof[2].picture[0].name")
					.value("test3.jpg")
			)
			.andExpect(jsonPath("proof[2].content")
				.doesNotExist()
			)
			.andExpect(jsonPath("proof[3].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).proofId())
			)
			.andExpect(jsonPath("proof[3].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(3).activityType().name())
			)
			.andExpect(jsonPath("proof[3].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).cCompanyId())
			)
			.andExpect(jsonPath("proof[3].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(3)
					.createdAt()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(
				jsonPath("proof[3].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[3].picture[0].url")
					.value("http://test4.com")
			)
			.andExpect(
				jsonPath("proof[3].picture[0].name")
					.value("test4.jpg")
			)
			.andExpect(jsonPath("proof[3].content")
				.doesNotExist()
			)
			.andExpect(jsonPath("proof[4].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).proofId())
			)
			.andExpect(jsonPath("proof[4].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(4).activityType().name())
			)
			.andExpect(jsonPath("proof[4].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).cCompanyId())
			)
			.andExpect(jsonPath("proof[4].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(4)
					.createdAt()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(
				jsonPath("proof[4].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[4].picture[0].url")
					.value("http://test5.com")
			)
			.andExpect(
				jsonPath("proof[4].picture[0].name")
					.value("test5.jpg")
			)
			.andExpect(jsonPath("proof[4].content")
				.doesNotExist()
			);

		verify(proofService).getMyProofList(testJwt, PageRequest.of(0, 5));
	}

	@Test
	@DisplayName("내 인증 목록 컨텐츠 없음")
	void getMyProofList_NO_CONTENT() throws Exception {
		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

		PageRequest pageRequest = PageRequest.of(0, 5);

		List<ProofDto> proofDtoList = new ArrayList<>();

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), proofDtoList.size());

		Page<ProofDto> proofPage = new PageImpl<>(proofDtoList.subList(start, end), pageRequest,
			proofDtoList.size());

		given(proofService.getMyProofList(anyString(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when & then
		this.mockMvc.perform(get("/proof/me")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@ParameterizedTest
	@CsvSource({"two, 1", "1, two"})
	@DisplayName("올바르지 않은 Query Param 을 입력 시 에러")
	void getMyProofList_Fail(String page, String size) throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

		// when & then
		this.mockMvc.perform(get("/proof/me")
				.param("page", page)
				.param("size", size)
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	private void generateProof(Long i) {
		List<ProofImageDto> proofImages1 = new ArrayList<>();
		proofImages1.add(ProofImageDto.builder()
			.proofImageId(1L)
			.fileName("test1.jpg")
			.fileUrl("http://test1.com")
			.build());

		ProofDto proof1 = ProofDto.builder()
			.proofId(5 * (i - 1) + 1)
			.memberId(i)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.proofImages(proofImages1)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImageDto> proofImages2 = new ArrayList<>();
		proofImages2.add(ProofImageDto.builder()
			.proofImageId(2L)
			.fileName("test2.jpg")
			.fileUrl("http://test2.com")
			.build());

		ProofDto proof2 = ProofDto.builder()
			.proofId(5 * (i - 1) + 2)
			.memberId(i)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.cCompanyId(2L)
			.proofImages(proofImages2)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImageDto> proofImages3 = new ArrayList<>();
		proofImages3.add(ProofImageDto.builder()
			.proofImageId(3L)
			.fileName("test3.jpg")
			.fileUrl("http://test3.com")
			.build());

		ProofDto proof3 = ProofDto.builder()
			.proofId(5 * (i - 1) + 3)
			.memberId(i)
			.activityType(ActivityType.MULTI_USE_CONTAINER)
			.cCompanyId(3L)
			.proofImages(proofImages3)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImageDto> proofImages4 = new ArrayList<>();
		proofImages4.add(ProofImageDto.builder()
			.proofImageId(4L)
			.fileName("test4.jpg")
			.fileUrl("http://test4.com")
			.build());

		ProofDto proof4 = ProofDto.builder()
			.proofId(5 * (i - 1) + 4)
			.memberId(i)
			.activityType(ActivityType.TUMBLER)
			.cCompanyId(4L)
			.proofImages(proofImages4)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImageDto> proofImages5 = new ArrayList<>();
		proofImages5.add(ProofImageDto.builder()
			.proofImageId(5L)
			.fileName("test5.jpg")
			.fileUrl("http://test5.com")
			.build());

		ProofDto proof5 = ProofDto.builder()
			.proofId(5 * (i - 1) + 5)
			.memberId(i)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.cCompanyId(5L)
			.proofImages(proofImages5)
			.createdAt(LocalDateTime.now())
			.build();

		if (i == 1) {
			EXPECTED_MY_PROOF_LIST.add(proof1);
			EXPECTED_MY_PROOF_LIST.add(proof2);
			EXPECTED_MY_PROOF_LIST.add(proof3);
			EXPECTED_MY_PROOF_LIST.add(proof4);
			EXPECTED_MY_PROOF_LIST.add(proof5);
		}
	}

}