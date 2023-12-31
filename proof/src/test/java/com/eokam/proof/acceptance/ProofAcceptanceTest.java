package com.eokam.proof.acceptance;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.eokam.proof.common.AcceptanceTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;
import com.eokam.proof.infrastructure.external.member.FollowList;
import com.eokam.proof.infrastructure.external.member.FollowMember;
import com.eokam.proof.infrastructure.external.member.FollowServiceFeign;
import com.eokam.proof.infrastructure.external.member.FollowStatus;
import com.eokam.proof.presentation.dto.request.ProofCreateRequest;
import com.eokam.proof.util.JwtUtil;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@Disabled
class ProofAcceptanceTest extends AcceptanceTest {
	private static final String API_BASE_PATH = "/proof";

	@Autowired
	private ProofRepository proofRepository;

	@Autowired
	private ProofImageRepository proofImageRepository;

	@MockBean
	private FollowServiceFeign followServiceFeign;

	private static final List<Proof> EXPECTED_MY_PROOF_LIST = new ArrayList<>();
	private static final List<Proof> EXPECTED_FRIEND_PROOF_LIST = new ArrayList<>();

	private static final List<Proof> EXPECTED_ALL_PROOF_LIST = new ArrayList<>();

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

		List<String> activityTypeList = response.body().jsonPath().getList("proof.activity_type");
		List<Integer> cCompanyIdList = response.body().jsonPath().getList("proof.c_company_id");
		List<String> createdAtList = response.body().jsonPath().getList("proof.created_at");
		List<List<String>> pictureUrlList = response.body().jsonPath().getList("proof.picture.url");
		List<List<String>> pictureNameList = response.body().jsonPath().getList("proof.picture.name");

		IntStream.range(0, 5).forEach(i -> {
			assertThat(activityTypeList.get(4 - i)).isEqualTo(EXPECTED_MY_PROOF_LIST.get(i).getActivityType().name());
			assertThat(Integer.toUnsignedLong(cCompanyIdList.get(i))).isEqualTo(
				EXPECTED_MY_PROOF_LIST.get(4 - i).getCCompanyId());
			assertThat(pictureUrlList.get(4 - i).get(0)).isEqualTo("http://test" + (i + 1) + ".com");
			assertThat(pictureNameList.get(4 - i).get(0)).isEqualTo("test" + (i + 1) + ".jpg");
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

	@Test
	@DisplayName("친구 인증 내역 조회를 성공한다.")
	void 친구_인증_내역_조회_성공() {
		// given
		LongStream.range(1, 6).forEach(this::인증_더미_데이터_생성);

		BDDMockito.given(followServiceFeign.isFollow(anyString(), anyLong()))
			.willReturn(new FollowStatus(2L, "ACCEPT"));

		// when
		ExtractableResponse<Response> response = 친구_인증_내역_조회(2L, 0L, 5L);

		// then
		assertThat(response.body().jsonPath().getList("proof")).hasSize(5);
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		List<Integer> proofIdList = response.body().jsonPath().getList("proof.proof_id");
		List<String> activityTypeList = response.body().jsonPath().getList("proof.activity_type");
		List<Integer> memberIdList = response.body().jsonPath().getList("proof.member_id");
		List<Integer> cCompanyIdList = response.body().jsonPath().getList("proof.c_company_id");
		List<String> createdAtList = response.body().jsonPath().getList("proof.created_at");
		List<List<String>> pictureUrlList = response.body().jsonPath().getList("proof.picture.url");
		List<List<String>> pictureNameList = response.body().jsonPath().getList("proof.picture.name");

		IntStream.range(0, 5).forEach(i -> {
			assertThat(activityTypeList.get(4 - i)).isEqualTo(
				EXPECTED_FRIEND_PROOF_LIST.get(i).getActivityType().name());
			assertThat(Integer.toUnsignedLong(memberIdList.get(i))).isEqualTo(
				EXPECTED_FRIEND_PROOF_LIST.get(4 - i).getMemberId());
			assertThat(Integer.toUnsignedLong(cCompanyIdList.get(i))).isEqualTo(
				EXPECTED_FRIEND_PROOF_LIST.get(4 - i).getCCompanyId());
			assertThat(pictureUrlList.get(4 - i).get(0)).isEqualTo("http://test" + (i + 1) + ".com");
			assertThat(pictureNameList.get(4 - i).get(0)).isEqualTo("test" + (i + 1) + ".jpg");
		});
	}

	@Test
	@DisplayName(("친구 인증 내역 조회 시 컨텐츠 없음을 반환한다."))
	void 친구_인증_내역_조회_컨텐츠없음() {
		// when
		BDDMockito.given(followServiceFeign.isFollow(anyString(), anyLong()))
			.willReturn(new FollowStatus(2L, "ACCEPT"));

		ExtractableResponse<Response> response = 친구_인증_내역_조회(2L, 0L, 5L);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	@Test
	@DisplayName("인증을 생성한다. (기타가 아닌 컨텐츠)")
	void 인증_생성() throws JSONException, IOException {
		// given
		ProofCreateRequest 생성_요청 = ProofCreateRequest.builder()
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.build();

		// when
		ExtractableResponse<Response> response = 인증_생성_시도(생성_요청);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank();
	}

	@Test
	@DisplayName("인증을 생성한다. (기타)")
	void 인증_생성_기타() throws JSONException, IOException {
		// given
		ProofCreateRequest 생성_요청 = ProofCreateRequest.builder()
			.activityType(ActivityType.ETC)
			.content("플로깅을 했다!")
			.build();

		// when
		ExtractableResponse<Response> response = 인증_생성_시도(생성_요청);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank();
	}

	@Test
	@DisplayName("내 인증을 상세 조회 한다.")
	void 내_인증_상세_조회() {
		// given
		Proof proof = proofRepository.save(Proof.builder()
			.memberId(1L)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test.jpg")
			.fileUrl("http://test.com")
			.proof(proof)
			.build());

		// when
		ExtractableResponse<Response> response = 인증_조회(proof.getProofId());

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		Long proofId = response.body().jsonPath().getLong("proof_id");
		ActivityType activityType = ActivityType.valueOf(response.body().jsonPath().getString("activity_type"));
		Long cCompanyId = response.body().jsonPath().getLong("c_company_id");
		String createdAt = response.body().jsonPath().getString("created_at");
		List<String> urlList = response.body().jsonPath().getList("picture.url");
		List<String> nameList = response.body().jsonPath().getList("picture.name");

		assertThat(proofId).isEqualTo(proof.getProofId());
		assertThat(activityType).isEqualTo(proof.getActivityType());
		assertThat(cCompanyId).isEqualTo(proof.getCCompanyId());
		assertThat(createdAt).isEqualTo(
			proof.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
		assertThat(urlList.get(0)).isEqualTo("http://test.com");
		assertThat(nameList.get(0)).isEqualTo("test.jpg");
		assertThat(proof.getContents()).isBlank();
	}

	@Test
	@DisplayName("친구 인증을 상세 조회 한다.")
	void 친구_인증_상세_조회() {
		// given
		Proof proof = proofRepository.save(Proof.builder()
			.memberId(2L)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test.jpg")
			.fileUrl("http://test.com")
			.proof(proof)
			.build());

		BDDMockito.given(followServiceFeign.isFollow(anyString(), anyLong()))
			.willReturn(new FollowStatus(2L, "ACCEPT"));

		// when
		ExtractableResponse<Response> response = 인증_조회(proof.getProofId());

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		Long proofId = response.body().jsonPath().getLong("proof_id");
		ActivityType activityType = ActivityType.valueOf(response.body().jsonPath().getString("activity_type"));
		Long cCompanyId = response.body().jsonPath().getLong("c_company_id");
		String createdAt = response.body().jsonPath().getString("created_at");
		List<String> urlList = response.body().jsonPath().getList("picture.url");
		List<String> nameList = response.body().jsonPath().getList("picture.name");

		assertThat(proofId).isEqualTo(proof.getProofId());
		assertThat(activityType).isEqualTo(proof.getActivityType());
		assertThat(cCompanyId).isEqualTo(proof.getCCompanyId());
		assertThat(createdAt).isEqualTo(
			proof.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
		assertThat(urlList.get(0)).isEqualTo("http://test.com");
		assertThat(nameList.get(0)).isEqualTo("test.jpg");
		assertThat(proof.getContents()).isBlank();
	}

	@Test
	@DisplayName("피드 목록을 조회한다.")
	void 천체_목록_조회() {
		LongStream.range(1, 6).forEach(this::인증_더미_데이터_생성);

		FollowList expectFList = new FollowList(new ArrayList<FollowMember>() {
			{
				new FollowMember(2L, 1000L, 0L, "http://profile2.com", "2번");
				new FollowMember(3L, 1000L, 0L, "http://profile3.com", "3번");
				new FollowMember(4L, 1000L, 0L, "http://profile4.com", "4번");
				new FollowMember(5L, 1000L, 0L, "http://profile5.com", "5번");
			}
		});

		BDDMockito.given(followServiceFeign.getFriends(anyString()))
			.willReturn(expectFList);

		// when
		ExtractableResponse<Response> response = 전체_인증_내역_조회(0L, 5L);

		// then
		assertThat(response.body().jsonPath().getList("proof")).hasSize(5);
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

		List<String> activityTypeList = response.body().jsonPath().getList("proof.activity_type");
		List<Integer> memberIdList = response.body().jsonPath().getList("proof.member_id");
		List<Integer> cCompanyIdList = response.body().jsonPath().getList("proof.c_company_id");
		List<List<String>> pictureUrlList = response.body().jsonPath().getList("proof.picture.url");
		List<List<String>> pictureNameList = response.body().jsonPath().getList("proof.picture.name");

		IntStream.range(0, 5).forEach(i -> {
			assertThat(activityTypeList.get(4 - i)).isEqualTo(EXPECTED_ALL_PROOF_LIST.get(i).getActivityType().name());
			assertThat(Integer.toUnsignedLong(memberIdList.get(i))).isEqualTo(
				EXPECTED_ALL_PROOF_LIST.get(4 - i).getMemberId());
			assertThat(Integer.toUnsignedLong(cCompanyIdList.get(i))).isEqualTo(
				EXPECTED_ALL_PROOF_LIST.get(4 - i).getCCompanyId());
			assertThat(pictureUrlList.get(4 - i).get(0)).isEqualTo("http://test" + (i + 1) + ".com");
			assertThat(pictureNameList.get(4 - i).get(0)).isEqualTo("test" + (i + 1) + ".jpg");
		});
	}

	private ExtractableResponse<Response> 전체_인증_내역_조회(Long page, Long size) {
		// given
		String testJwt = JWT_발급(1L);

		return given().log().all()
			.when()
			.cookie("access-token", testJwt)
			.get(API_BASE_PATH + "/feed?page=" + page.toString() + "&size=" + size.toString())
			.then().log().all()
			.extract();
	}

	private static String JWT_발급(Long memberId) {
		return JwtUtil.createAccessToken(memberId);
	}

	private ExtractableResponse<Response> 인증_조회(long l) {
		long memberId = 1L;
		// given
		String testJwt = JWT_발급(memberId);

		return given().log().all()
			.when()
			.cookie("access-token", testJwt)
			.get(API_BASE_PATH + "/" + l)
			.then().log().all()
			.extract();
	}

	private ExtractableResponse<Response> 인증_생성_시도(ProofCreateRequest 생성_요청) throws JSONException, IOException {
		long memberId = 1L;
		// given
		String testJwt = JWT_발급(memberId);

		final ClassPathResource resource = new ClassPathResource("static/earth.jpg");

		JSONObject json = new JSONObject();
		json.put("activity_type", 생성_요청.activityType());
		json.put("c_company_id", 생성_요청.cCompanyId());
		json.put("content", 생성_요청.content());

		return given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.cookie("access-token", testJwt)
			.multiPart("file", resource.getFile())
			.multiPart(new MultiPartSpecBuilder(json.toString())
				.fileName("content")
				.controlName("content")
				.mimeType("application/json")
				.build()
			)
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.log().all()
			.when()
			.post(API_BASE_PATH)
			.then().log().all()
			.extract();
	}

	private static ExtractableResponse<Response> 내_인증_내역_조회(Long page, Long size) {
		long memberId = 1L;
		// given
		String testJwt = JWT_발급(memberId);

		return given().log().all()
			.when()
			.cookie("access-token", testJwt)
			.get(API_BASE_PATH + "/me?page=" + page.toString() + "&size=" + size.toString())
			.then().log().all()
			.extract();
	}

	private static ExtractableResponse<Response> 친구_인증_내역_조회(Long friendId, Long page, Long size) {
		long memberId = 1L;
		// given
		String testJwt = JWT_발급(memberId);

		return given().log().all()
			.when()
			.cookie("access-token", testJwt)
			.get(API_BASE_PATH + "?memberId=" + friendId.toString() + "&page=" + page.toString() + "&size="
				+ size.toString())
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

		EXPECTED_ALL_PROOF_LIST.add(proof1);

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

		EXPECTED_ALL_PROOF_LIST.add(proof2);

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

		EXPECTED_ALL_PROOF_LIST.add(proof3);

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

		EXPECTED_ALL_PROOF_LIST.add(proof4);

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

		EXPECTED_ALL_PROOF_LIST.add(proof5);

		if (i == 1) {
			EXPECTED_MY_PROOF_LIST.add(proof1);
			EXPECTED_MY_PROOF_LIST.add(proof2);
			EXPECTED_MY_PROOF_LIST.add(proof3);
			EXPECTED_MY_PROOF_LIST.add(proof4);
			EXPECTED_MY_PROOF_LIST.add(proof5);
		}

		if (i == 2) {
			EXPECTED_FRIEND_PROOF_LIST.add(proof1);
			EXPECTED_FRIEND_PROOF_LIST.add(proof2);
			EXPECTED_FRIEND_PROOF_LIST.add(proof3);
			EXPECTED_FRIEND_PROOF_LIST.add(proof4);
			EXPECTED_FRIEND_PROOF_LIST.add(proof5);
		}
	}

}
