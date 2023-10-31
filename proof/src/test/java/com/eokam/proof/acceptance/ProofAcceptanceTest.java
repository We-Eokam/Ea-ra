package com.eokam.proof.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.eokam.proof.common.AcceptanceTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class ProofAcceptanceTest extends AcceptanceTest {
	private static final String API_BASE_PATH = "/proof";

	@Autowired
	private ProofRepository proofRepository;

	@Autowired
	private ProofImageRepository proofImageRepository;

	private static final List<Proof> EXPECTED_MY_PROOF_LIST = new ArrayList<>();

	@Test
	@DisplayName("내 인증 내역 조회를 성공한다.")
	void 내_인증_내역_조회_성공() {
		// given
		LongStream.range(1, 6).forEach(this::인증_더미_데이터_생성);

		// when
		ExtractableResponse<Response> response = 내_인증_내역_조회(0L, 5L);

		// then
		assertThat(response.body().jsonPath().getList("proof")).hasSize(5);
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		List<Integer> proofIdList = response.body().jsonPath().getList("proof.proof_id");
		List<String> activityTypeList = response.body().jsonPath().getList("proof.activity_type");
		List<Integer> cCompanyIdList = response.body().jsonPath().getList("proof.c_company_id");
		List<String> createdAtList = response.body().jsonPath().getList("proof.created_at");
		List<List<String>> pictureUrlList = response.body().jsonPath().getList("proof.picture.url");
		List<List<String>> pictureNameList = response.body().jsonPath().getList("proof.picture.name");

		IntStream.range(0, 5).forEach(i -> {
			assertThat(proofIdList.get(i).toString()).isEqualTo(EXPECTED_MY_PROOF_LIST.get(i).getProofId().toString());
			assertThat(activityTypeList.get(i)).isEqualTo(EXPECTED_MY_PROOF_LIST.get(i).getActivityType().toString());
			assertThat(cCompanyIdList.get(i).toString()).isEqualTo(
				EXPECTED_MY_PROOF_LIST.get(i).getCCompanyId().toString());
			assertThat(createdAtList.get(i)).isEqualTo(EXPECTED_MY_PROOF_LIST.get(i)
				.getCreatedAt()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
			assertThat(pictureUrlList.get(i).get(0)).isEqualTo("http://test" + (i + 1) + ".com");
			assertThat(pictureNameList.get(i).get(0)).isEqualTo("test" + (i + 1) + ".jpg");
		});
	}

	@Test
	@DisplayName(("내 인증 내역 조회 시 컨텐츠 없음을 반환한다."))
	void 내_인증_내역_조회_컨텐츠없음() {
		// when
		ExtractableResponse<Response> response = 내_인증_내역_조회(0L, 5L);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	private static ExtractableResponse<Response> 내_인증_내역_조회(Long page, Long size) {
		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

		return RestAssured.given().log().all()
			.when()
			.cookie("access-token", testJwt)
			.get(API_BASE_PATH + "/me?page=" + page.toString() + "&size=" + size.toString())
			.then().log().all()
			.extract();
	}

	private void 인증_더미_데이터_생성(Long i) {
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
