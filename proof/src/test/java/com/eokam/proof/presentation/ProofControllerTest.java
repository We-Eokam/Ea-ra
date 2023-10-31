package com.eokam.proof.presentation;

import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.eokam.proof.common.BaseControllerTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;

import jakarta.servlet.http.Cookie;

class ProofControllerTest extends BaseControllerTest {

	@Autowired
	ProofRepository proofRepository;

	@Autowired
	ProofImageRepository proofImageRepository;

	private static final List<Proof> EXPECTED_MY_PROOF_LIST = new ArrayList<>();

	@Test
	@DisplayName("내 인증 목록 조회 성공")
	void getMyProofList_Success() throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

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
				.value(EXPECTED_MY_PROOF_LIST.get(0).getProofId())
			)
			.andExpect(jsonPath("proof[0].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(0).getActivityType().name())
			)
			.andExpect(jsonPath("proof[0].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).getCCompanyId())
			)
			.andExpect(jsonPath("proof[0].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(0)
					.getCreatedAt()
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
				.value(EXPECTED_MY_PROOF_LIST.get(1).getProofId())
			)
			.andExpect(jsonPath("proof[1].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(1).getActivityType().name())
			)
			.andExpect(jsonPath("proof[1].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).getCCompanyId())
			)
			.andExpect(jsonPath("proof[1].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(1)
					.getCreatedAt()
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
				.value(EXPECTED_MY_PROOF_LIST.get(2).getProofId())
			)
			.andExpect(jsonPath("proof[2].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(2).getActivityType().name())
			)
			.andExpect(jsonPath("proof[2].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).getCCompanyId())
			)
			.andExpect(jsonPath("proof[2].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(2)
					.getCreatedAt()
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
				.value(EXPECTED_MY_PROOF_LIST.get(3).getProofId())
			)
			.andExpect(jsonPath("proof[3].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(3).getActivityType().name())
			)
			.andExpect(jsonPath("proof[3].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).getCCompanyId())
			)
			.andExpect(jsonPath("proof[3].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(3)
					.getCreatedAt()
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
				.value(EXPECTED_MY_PROOF_LIST.get(4).getProofId())
			)
			.andExpect(jsonPath("proof[4].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(4).getActivityType().name())
			)
			.andExpect(jsonPath("proof[4].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).getCCompanyId())
			)
			.andExpect(jsonPath("proof[4].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(4)
					.getCreatedAt()
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
			)
			.andDo(document("내 인증 목록 조회",
					requestCookies(cookieWithName("access-token").description("액세스 토큰")),
					responseFields(
						fieldWithPath("proof").description("인증 목록 배열"),
						fieldWithPath("proof[].proof_id").description("인증 ID"),
						fieldWithPath("proof[].activity_type").description("활동 타입"),
						fieldWithPath("proof[].c_company_id").description("탄소 중립 포인트 제도 기업 ID"),
						fieldWithPath("proof[].created_at").description("인증 생성 시간"),
						fieldWithPath("proof[].picture").description("인증 사진 배열"),
						fieldWithPath("proof[].picture[].url").description("인증 사진 URL"),
						fieldWithPath("proof[].picture[].name").description("인증 사진 파일 명"),
						fieldWithPath("proof[].content").description("기타 인증의 경우 내용")
					)
				)
			);

	}

	@ParameterizedTest
	@CsvSource({"1, 0", "-1, 1", "two, 1", "1, two"})
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
		Proof proof1 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test1.jpg")
			.fileUrl("http://test1.com")
			.proof(proof1)
			.build());

		Proof proof2 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.cCompanyId(2L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test2.jpg")
			.fileUrl("http://test2.com")
			.proof(proof2)
			.build());

		Proof proof3 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.MULTI_USE_CONTAINER)
			.cCompanyId(3L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test3.jpg")
			.fileUrl("http://test3.com")
			.proof(proof3)
			.build());

		Proof proof4 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.TUMBLER)
			.cCompanyId(4L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test4.jpg")
			.fileUrl("http://test4.com")
			.proof(proof4)
			.build());

		Proof proof5 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.cCompanyId(5L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test5.jpg")
			.fileUrl("http://test5.com")
			.proof(proof5)
			.build());

		if (i == 1) {
			EXPECTED_MY_PROOF_LIST.add(proof1);
			EXPECTED_MY_PROOF_LIST.add(proof2);
			EXPECTED_MY_PROOF_LIST.add(proof3);
			EXPECTED_MY_PROOF_LIST.add(proof4);
			EXPECTED_MY_PROOF_LIST.add(proof5);
		}
	}

}