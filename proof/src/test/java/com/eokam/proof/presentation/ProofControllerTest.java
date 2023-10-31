package com.eokam.proof.presentation;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

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
				.param("page", "1")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().exists(HttpHeaders.COOKIE))
			.andExpect(jsonPath("proof")
				.isArray()
			)
			.andExpect(jsonPath("proof[0].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).getProofId())
			)
			.andExpect(jsonPath("proof[0].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(0).getActivityType())
			)
			.andExpect(jsonPath("proof[0].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).getCCompanyId())
			)
			.andExpect(jsonPath("proof[0].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(0).getCreatedAt())
			)
			.andExpect(
				jsonPath("proof[0].picture[0].url")
					.value(EXPECTED_MY_PROOF_LIST.get(0).getProofImages().get(0).getFileUrl())
			)
			.andExpect(
				jsonPath("proof[0].picture[0].name")
					.value(EXPECTED_MY_PROOF_LIST.get(0).getProofImages().get(0).getFileName())
			)
			.andExpect(jsonPath("proof[0].content")
				.value("null")
			)
			.andExpect(jsonPath("proof[1].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).getProofId())
			)
			.andExpect(jsonPath("proof[1].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(1).getActivityType())
			)
			.andExpect(jsonPath("proof[1].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).getCCompanyId())
			)
			.andExpect(jsonPath("proof[1].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(1).getCreatedAt())
			)
			.andExpect(
				jsonPath("proof[1].picture[0].url")
					.value(EXPECTED_MY_PROOF_LIST.get(1).getProofImages().get(0).getFileUrl())
			)
			.andExpect(
				jsonPath("proof[1].picture[0].name")
					.value(EXPECTED_MY_PROOF_LIST.get(1).getProofImages().get(0).getFileName())
			)
			.andExpect(jsonPath("proof[1].content")
				.value("null")
			)
			.andExpect(jsonPath("proof[2].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).getProofId())
			)
			.andExpect(jsonPath("proof[2].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(2).getActivityType())
			)
			.andExpect(jsonPath("proof[2].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).getCCompanyId())
			)
			.andExpect(jsonPath("proof[2].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(2).getCreatedAt())
			)
			.andExpect(
				jsonPath("proof[2].picture[0].url")
					.value(EXPECTED_MY_PROOF_LIST.get(2).getProofImages().get(0).getFileUrl())
			)
			.andExpect(
				jsonPath("proof[2].picture[0].name")
					.value(EXPECTED_MY_PROOF_LIST.get(2).getProofImages().get(0).getFileName())
			)
			.andExpect(jsonPath("proof[2].content")
				.value("null")
			)
			.andExpect(jsonPath("proof[3].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).getProofId())
			)
			.andExpect(jsonPath("proof[3].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(3).getActivityType())
			)
			.andExpect(jsonPath("proof[3].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).getCCompanyId())
			)
			.andExpect(jsonPath("proof[3].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(3).getCreatedAt())
			)
			.andExpect(
				jsonPath("proof[3].picture[0].url")
					.value(EXPECTED_MY_PROOF_LIST.get(3).getProofImages().get(0).getFileUrl())
			)
			.andExpect(
				jsonPath("proof[3].picture[0].name")
					.value(EXPECTED_MY_PROOF_LIST.get(3).getProofImages().get(0).getFileName())
			)
			.andExpect(jsonPath("proof[3].content")
				.value("null")
			)
			.andExpect(jsonPath("proof[4].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).getProofId())
			)
			.andExpect(jsonPath("proof[4].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(4).getActivityType())
			)
			.andExpect(jsonPath("proof[4].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).getCCompanyId())
			)
			.andExpect(jsonPath("proof[4].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(4).getCreatedAt())
			)
			.andExpect(
				jsonPath("proof[4].picture[0].url")
					.value(EXPECTED_MY_PROOF_LIST.get(4).getProofImages().get(0).getFileUrl())
			)
			.andExpect(
				jsonPath("proof[4].picture[0].name")
					.value(EXPECTED_MY_PROOF_LIST.get(4).getProofImages().get(0).getFileName())
			)
			.andExpect(jsonPath("proof[4].content")
				.value("null")
			)
			.andDo(document("내 인증 목록 조회",
					requestHeaders(
						headerWithName(HttpHeaders.COOKIE).description("Access Token"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
					),
					responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
					),
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