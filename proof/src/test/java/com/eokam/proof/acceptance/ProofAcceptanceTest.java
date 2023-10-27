package com.eokam.proof.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofRepository;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ProofAcceptanceTest extends AcceptanceTest {
	private static final String API_BASH_PATH = "/proof";

	@Autowired
	private ProofRepository proofRepository;

	@Test
	@DisplayName("내 인증 내역 조회를 성공한다.")
	public void 내_인증_내역_조회_성공() {
		// given
		List<Proof> proofList = 인증_더미_데이터_생성();

		// when
		ExtractableResponse<Response> response = 내_인증_내역_조회(1L, 5L);

		// then
		assertThat(response.body().jsonPath().getList("proof").size()).isEqualTo(5L);
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		List<Object> proofIdList = response.body().jsonPath().getList("proof.proof_id");
		List<Object> activityTypeList = response.body().jsonPath().getList("proof.activity_type");
		List<Object> cCompanyIdList = response.body().jsonPath().getList("proof.c_company_id");
		List<Object> createdAtList = response.body().jsonPath().getList("proof.created_at");
		List<Object> pictureList = response.body().jsonPath().getList("proof.picture");

		for (int i = 0; i < 5; i++) {
			assertThat(proofIdList.get(i).toString())
				.isEqualTo(
					proofList.get(i).getProofId().toString()
				);
			assertThat(activityTypeList.get(i).toString())
				.isEqualTo(
					proofList.get(i).getActivityType().toString()
				);
			assertThat(cCompanyIdList.get(i).toString())
				.isEqualTo(
					proofList.get(i).getCCompanyId().toString()
				);
			assertThat(createdAtList.get(i).toString())
				.isEqualTo(
					proofList.get(i).getCreatedAt().toString()
				);
			assertThat(pictureList.get(i).toString())
				.isEqualTo(
					proofList.get(i).getProofImages().get(0).getFileUrl()
				);
		}
	}

	private static ExtractableResponse<Response> 내_인증_내역_조회(Long pageNo, Long size) {
		return RestAssured.given().log().all()
			.when()
			.get(API_BASH_PATH + "/me?page=" + pageNo.toString() + "&size=" + size.toString())
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
					.CCompanyId(1L)
					.build()
				)
			);
		}
		return proofList;
	}

}
