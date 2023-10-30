package com.eokam.proof.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.eokam.proof.common.AcceptanceTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofRepository;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class ProofAcceptanceTest extends AcceptanceTest {
	private static final String API_BASE_PATH = "/proof";

	@Autowired
	private ProofRepository proofRepository;

	@Test
	@DisplayName("내 인증 내역 조회를 성공한다.")
	void 내_인증_내역_조회_성공() {
		// given
		List<Proof> proofList = 인증_더미_데이터_생성();

		// when
		ExtractableResponse<Response> response = 내_인증_내역_조회(1L, 5L);

		// then
		assertThat(response.body().jsonPath().getList("proof")).hasSize(5);
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		List<Long> proofIdList = response.body().jsonPath().getList("proof.proof_id");
		List<ActivityType> activityTypeList = response.body().jsonPath().getList("proof.activity_type");
		List<Long> cCompanyIdList = response.body().jsonPath().getList("proof.c_company_id");
		List<LocalDateTime> createdAtList = response.body().jsonPath().getList("proof.created_at");
		List<String> pictureList = response.body().jsonPath().getList("proof.picture");

		IntStream.range(0, 5).forEach(i -> {
			assertThat(proofIdList.get(i)).isEqualTo(proofList.get(i).getProofId());
			assertThat(activityTypeList.get(i)).isEqualTo(proofList.get(i).getActivityType());
			assertThat(cCompanyIdList.get(i)).isEqualTo(proofList.get(i).getCCompanyId());
			assertThat(createdAtList.get(i)).isEqualTo(proofList.get(i).getCreatedAt());
			assertThat(pictureList.get(i)).isEqualTo(proofList.get(i).getProofImages().get(0).getFileUrl());
		});
	}

	private static ExtractableResponse<Response> 내_인증_내역_조회(Long pageNo, Long size) {
		return RestAssured.given().log().all()
			.when()
			.get(API_BASE_PATH + "/me?page=" + pageNo.toString() + "&size=" + size.toString())
			.then().log().all()
			.extract();
	}

	private List<Proof> 인증_더미_데이터_생성() {
		List<Proof> proofList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			List<ProofImage> imageList = new ArrayList<>();
			imageList.add(
				ProofImage.builder()
					.fileName(Integer.toString(i))
					.fileUrl(Integer.toString(i))
					.build()
			);

			proofList.add(
				proofRepository.save(Proof.builder()
					.memberId(1L)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.proofImages(imageList)
					.cCompanyId(1L)
					.build()
				)
			);
		}
		return proofList;
	}

}
