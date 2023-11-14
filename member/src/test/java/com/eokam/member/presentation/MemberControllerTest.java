package com.eokam.member.presentation;


import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.SavingType;
import com.eokam.member.infra.dto.FollowStatus;
import com.eokam.member.infra.repository.MemberRepository;
import com.eokam.member.presentation.common.BasicControllerTest;
import com.eokam.member.presentation.dto.BillRequest;
import com.eokam.member.presentation.dto.MemberFollowRequest;
import com.eokam.member.presentation.dto.MemberNicknameUpdateRequest;
import com.eokam.member.presentation.dto.MemberProfileResponse;
import com.eokam.member.presentation.dto.MemberTestDoneRequest;
import com.eokam.member.presentation.dto.RepayGrooRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class MemberControllerTest extends BasicControllerTest {

	@Test
	void 멤버상세조회(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		RequestSpecification retrieveMemberDetail = RestAssured.given(documentationSpec)
			.queryParam("memberId",memberId)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",

				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("멤버 PK")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("멤버 닉네임 (초기는 카카오 닉네임)"),
					fieldWithPath("groo").type(JsonFieldType.NUMBER).description("유저의 남은 그루"),
					fieldWithPath("bill").type(JsonFieldType.NUMBER).description("유저 고소장 개수"),
					fieldWithPath("bill_count").type(JsonFieldType.NUMBER).description("유저 고소장 카운트 (3개 모이면 고소장 하나)"),
					fieldWithPath("profile_image_url").type(JsonFieldType.STRING).description("유저 프로필 사진 url(초기는 카카오 프로필)"),
					fieldWithPath("is_test_done").type(JsonFieldType.BOOLEAN).description("테스트 완료 여부")
				)
			));

		//when & then
		var 결과 = retrieveMemberDetail.when()
			.get("/member/detail")
			.then()
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id"))
			.isNotNull()
			.isEqualTo(memberId);
	}

	@Test
	void 멤버상세조회실패_존재하지않는멤버(){
		//given
		Long memberId = 0L;

		RequestSpecification retrieveMemberDetail = RestAssured.given(documentationSpec)
			.queryParam("memberId",memberId)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",

				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
			));

		//when & then
		var 결과 = retrieveMemberDetail.when()
			.get("/member/detail")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());

	}

	@Test
	void 멤버_프로필_리스트조회(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		Long memberId2 = 닉네임이나는감자인_프로필사진이범고래인유저생성();
		Long memberId3 = 닉네임이어캄인_프로필사진이범고래인유저생성();
		Long memberId4 = 닉네임이나는문어인_프로필사진이펭귄인유저생성();

		RequestSpecification retrieveMemberDetail = RestAssured.given(documentationSpec)
			.queryParam("memberId",memberId)
			.queryParam("memberId",memberId2)
			.queryParam("memberId",memberId3)
			.queryParam("memberId",memberId4)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",

				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("멤버 PK (여러개 가능 상단 요청 참조)")
				),
				responseFields(
					fieldWithPath("member_list").type(JsonFieldType.ARRAY).description("멤버 프로필 리스트"),
					fieldWithPath("member_list.[].member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("member_list.[].nickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
					fieldWithPath("member_list.[].groo").type(JsonFieldType.NUMBER).description("멤버의 남은 그루"),
					fieldWithPath("member_list.[].profile_image_url").type(JsonFieldType.STRING).description("멤버 프로필 사진 url(초기는 카카오 프로필)")
				)
			));

		//when & then
		var 결과 = retrieveMemberDetail.when()
			.get("/member")
			.then()
			.extract()
			.jsonPath();

		assertThat(결과.getList("member_list", MemberProfileResponse.class))
			.hasSize(4)
			.extracting(MemberProfileResponse::getMemberId)
			.contains(memberId,memberId2,memberId3,memberId4);

		assertThat(결과.getList("member_list", MemberProfileResponse.class))
			.hasSize(4)
			.extracting(MemberProfileResponse::getNickname)
			.contains("나는문어","꿈을꾸는문어","어캄","나는감자");
	}

	@Test
	void 멤버_프로필_리스트조회_실패_존재하지않는멤버(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		Long memberId2 = 닉네임이나는감자인_프로필사진이범고래인유저생성();
		Long memberId3 = 0L;
		Long memberId4 = 닉네임이나는문어인_프로필사진이펭귄인유저생성();

		RequestSpecification retrieveMemberDetail = RestAssured.given(documentationSpec)
			.queryParam("memberId",memberId)
			.queryParam("memberId",memberId2)
			.queryParam("memberId",memberId3)
			.queryParam("memberId",memberId4)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",

				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("멤버 PK (여러개 가능 상단 요청 참조)")
				)
			));

		//when & then
		var 결과 = retrieveMemberDetail.when()
			.get("/member")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void 그루_적립(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		RepayGrooRequest repayGrooRequest = RepayGrooRequest.builder().groo(300).memberId(memberId).savingType(
			SavingType.ACCUSATION).build();


		RequestSpecification saveGroo = RestAssured.given(documentationSpec)
			.body(repayGrooRequest)
			.contentType(ContentType.JSON)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",

				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("saving_type").type(JsonFieldType.STRING).description("적립 타입 고발(ACCUSATION) 인증(PROOF)"),
					fieldWithPath("groo").type(JsonFieldType.NUMBER).description("적립할 그루")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
					fieldWithPath("groo").type(JsonFieldType.NUMBER).description("갚아야할 그루"),
					fieldWithPath("bill").type(JsonFieldType.NUMBER).description("내 고발장 수"),
					fieldWithPath("bill_count").type(JsonFieldType.NUMBER).description("내 고발장 카운트 수"),
					fieldWithPath("profile_image_url").type(JsonFieldType.STRING).description("멤버 프로필 사진 url"),
					fieldWithPath("is_test_done").type(JsonFieldType.BOOLEAN).description("테스트 완료 여부")
				)
			));

		//when & then
		var 결과 = saveGroo.when()
			.post("/member/groo")
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberId);
		assertThat(결과.getInt("groo")).isEqualTo(300);
		assertThat(결과.getString("nickname")).isEqualTo("꿈을꾸는문어");
	}

	@Test
	void 그루_적립_실패_멤버없음(){
		//given
		Long memberId = 300L;
		RepayGrooRequest repayGrooRequest = RepayGrooRequest.builder().groo(300).memberId(memberId).savingType(
			SavingType.ACCUSATION).build();


		RequestSpecification saveGroo = RestAssured.given(documentationSpec)
			.body(repayGrooRequest)
			.contentType(ContentType.JSON)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",

				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
			));

		//when & then
		var 결과 = saveGroo.when()
			.post("/member/groo")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void 고소장_사용(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		해당유저_고소장1개증가(memberId);
		BillRequest billRequest = BillRequest.builder().memberId(memberId).build();

		RequestSpecification useBill = RestAssured.given(documentationSpec)
			.body(billRequest)
			.contentType(ContentType.JSON)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 ID")
				)
			));

		//when & then
		var 결과 = useBill.when()
			.delete("/member/accusation")
			.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void 고소장_사용_고소장없음(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		BillRequest billRequest = BillRequest.builder().memberId(memberId).build();

		RequestSpecification useBill = RestAssured.given(documentationSpec)
			.body(billRequest)
			.contentType(ContentType.JSON)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
			));

		//when & then
		var 결과 = useBill.when()
			.delete("/member/accusation")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void 고소장_카운트_증가(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		BillRequest billRequest = BillRequest.builder().memberId(memberId).build();

		RequestSpecification useBill = RestAssured.given(documentationSpec)
			.body(billRequest)
			.contentType(ContentType.JSON)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 ID")
				)
			));

		//when & then
		var 결과 = useBill.when()
			.post("/member/accusation/count")
			.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void 중복_닉네임_검사_중복아님(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		String nickname = "나는문어";

		RequestSpecification checkDuplicateNickname = RestAssured.given(documentationSpec)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				pathParameters(
					parameterWithName("nickname").description("중복 검사 대상 닉네임 /member/nickname/{nickname}")
				)
			));

		//when & then
		var 결과 = checkDuplicateNickname.when()
			.get("/member/nickname/{nickname}",nickname)
			.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void 중복_닉네임_검사_중복임(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		String nickname = "꿈을꾸는문어";

		RequestSpecification checkDuplicateNickname = RestAssured.given(documentationSpec)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				pathParameters(
					parameterWithName("nickname").description("중복 검사 대상 닉네임 /member/nickname/{nickname}")
				)
			));

		//when & then
		var 결과 = checkDuplicateNickname.when()
			.get("/member/nickname/{nickname}",nickname)
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void 닉네임변경(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		String nickname = "나는문어";

		MemberNicknameUpdateRequest memberNicknameUpdateRequest =
			MemberNicknameUpdateRequest.builder().memberId(memberId).nickname(nickname).build();

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.body(memberNicknameUpdateRequest)
			.contentType(ContentType.JSON)
			.cookie("access-token",해당유저_JWT쿠키생성(memberId))
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버PK"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.put("/member/nickname")
			.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void 닉네임변경_실패_중복된닉네임(){
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();
		닉네임이나는감자인_프로필사진이범고래인유저생성();

		String nickname = "나는감자";

		MemberNicknameUpdateRequest memberNicknameUpdateRequest =
			MemberNicknameUpdateRequest.builder().memberId(memberId).nickname(nickname).build();

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.log().all()
			.body(memberNicknameUpdateRequest)
			.contentType(ContentType.JSON)
			.cookie("access-token",해당유저_JWT쿠키생성(memberId))
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.put("/member/nickname")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void 프로필사진_변경() throws IOException {
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();


		ClassPathResource resource = new ClassPathResource("static/eora.png");


		String 변경할_파일이름 = "eora";
		String 변경할_파일이름_확장자 = "eora.png";

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.multiPart("profile_image",resource.getFile())
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.cookie("access-token",해당유저_JWT쿠키생성(memberId))
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestParts(
					partWithName("profile_image").description("변경할 프로필 이미지 (멀티파트)")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.post("/member/profileImage")
			.then()
			.statusCode(HttpStatus.OK.value());

	}

	@Test
	void 프로필사진_변경실패_S3업로드실패() throws IOException {
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();


		ClassPathResource resource = new ClassPathResource("static/eora.png");


		String 변경할_파일이름 = "eora";
		String 변경할_파일이름_확장자 = "eora.png";

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.cookie("access-token",해당유저_JWT쿠키생성(memberId))
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
			));

		//when & then
		var 결과 = updateNickname.when()
			.post("/member/profileImage")
			.then()
			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	void 테스트완료() throws IOException {
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		MemberTestDoneRequest memberTestDoneRequest =
			MemberTestDoneRequest.builder().memberId(memberId).groo(300).build();

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(memberTestDoneRequest)
			.cookie("access-token",해당유저_JWT쿠키생성(memberId))
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK가 필요합니다"),
					fieldWithPath("groo").type(JsonFieldType.NUMBER).description("테스트 결과로 적립할 그루")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("멤버 닉네임 (초기는 카카오 닉네임)"),
					fieldWithPath("groo").type(JsonFieldType.NUMBER).description("유저의 남은 그루"),
					fieldWithPath("bill").type(JsonFieldType.NUMBER).description("유저 고소장 개수"),
					fieldWithPath("bill_count").type(JsonFieldType.NUMBER).description("유저 고소장 카운트 (3개 모이면 고소장 하나)"),
					fieldWithPath("profile_image_url").type(JsonFieldType.STRING).description("유저 프로필 사진 url(초기는 카카오 프로필)"),
					fieldWithPath("is_test_done").type(JsonFieldType.BOOLEAN).description("테스트 완료 여부")
				)

			));

		//when & then
		var 결과 = updateNickname.when()
			.put("/member/test")
			.then()
			.statusCode(HttpStatus.OK.value()).extract().jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberId);
		assertThat(결과.getInt("groo")).isEqualTo(300);
		assertThat(결과.getBoolean("is_test_done")).isTrue();
	}

	@Test
	void 테스트완료실패_이미테스트완료() throws IOException {
		//given
		Long memberId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		해당유저_테스트완료(memberId);
		MemberTestDoneRequest memberTestDoneRequest =
			MemberTestDoneRequest.builder().memberId(memberId).groo(300).build();

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(memberTestDoneRequest)
			.cookie("access-token",해당유저_JWT쿠키생성(memberId))
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK가 필요합니다"),
					fieldWithPath("groo").type(JsonFieldType.NUMBER).description("테스트 결과로 적립할 그루")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.put("/member/test")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void 팔로우여부체크성공_서로팔로우안함() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 닉네임이어캄인_프로필사진이범고래인유저생성();

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.param("memberId",memberBId.intValue())
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("팔로우 여부 체크할 상대 멤버 ID")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("상대 멤버 ID"),
					fieldWithPath("follow_status").type(JsonFieldType.STRING).description("상대와의 팔로우 상태")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.get("/member/follow")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberBId);
		assertThat(결과.getString("follow_status")).isEqualTo(FollowStatus.NOTHING.toString());
	}

	@Test
	void 팔로우여부체크성공_나만팔로우함() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 닉네임이어캄인_프로필사진이범고래인유저생성();

		A유저가B유저에게_팔로우(memberAId,memberBId);

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.param("memberId",memberBId.intValue())
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("팔로우 여부 체크할 상대 멤버 ID")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("상대 멤버 ID"),
					fieldWithPath("follow_status").type(JsonFieldType.STRING).description("상대와의 팔로우 상태")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.get("/member/follow")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberBId);
		assertThat(결과.getString("follow_status")).isEqualTo(FollowStatus.REQUEST.toString());
	}

	@Test
	void 팔로우여부체크성공_상대만팔로우함() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 닉네임이어캄인_프로필사진이범고래인유저생성();

		A유저가B유저에게_팔로우(memberBId,memberAId);

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.param("memberId",memberBId.intValue())
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("팔로우 여부 체크할 상대 멤버 ID")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("상대 멤버 ID"),
					fieldWithPath("follow_status").type(JsonFieldType.STRING).description("상대와의 팔로우 상태")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.get("/member/follow")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberBId);
		assertThat(결과.getString("follow_status")).isEqualTo(FollowStatus.ACCEPT.toString());
	}

	@Test
	void 팔로우여부체크성공_서로팔로우함_친구사이() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 닉네임이어캄인_프로필사진이범고래인유저생성();

		A유저가B유저에게_팔로우(memberBId,memberAId);

		A유저가B유저에게_팔로우(memberAId,memberBId);

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.param("memberId",memberBId.intValue())
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("팔로우 여부 체크할 상대 멤버 ID")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("상대 멤버 ID"),
					fieldWithPath("follow_status").type(JsonFieldType.STRING).description("상대와의 팔로우 상태")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.get("/member/follow")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberBId);
		assertThat(결과.getString("follow_status")).isEqualTo(FollowStatus.FRIEND.toString());
	}

	@Test
	void 팔로우여부체크실패_존재하지않는멤버() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 100000L;

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.param("memberId",memberBId.intValue())
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				queryParameters(
					parameterWithName("memberId").description("팔로우 여부 체크할 상대 멤버 ID")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.get("/member/follow")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void 상대_팔로우하기() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 닉네임이어캄인_프로필사진이범고래인유저생성();

		MemberFollowRequest memberFollowRequest = MemberFollowRequest.builder().targetId(memberBId).build();

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.contentType(ContentType.JSON)
			.body(memberFollowRequest)
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("target_id").type(JsonFieldType.NUMBER).description("팔로우할 상대 ID")
				),
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("상대 멤버 ID"),
					fieldWithPath("follow_status").type(JsonFieldType.STRING).description("상대와의 팔로우 상태")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.post("/member/follow")
			.then()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.jsonPath();

		assertThat(결과.getLong("member_id")).isEqualTo(memberBId);
		assertThat(결과.getString("follow_status")).isEqualTo(FollowStatus.REQUEST.toString());
	}

	@Test
	void 나랑_친구인사람_리스트가져오기() throws IOException {
		//given
		Long memberAId = 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성();

		Long memberBId = 닉네임이어캄인_프로필사진이범고래인유저생성();

		Long memberCId = 닉네임이나는문어인_프로필사진이펭귄인유저생성();

		Long memberDId = 닉네임이나는감자인_프로필사진이범고래인유저생성();

		A유저가B유저에게_팔로우(memberAId,memberBId);
		A유저가B유저에게_팔로우(memberBId,memberAId);

		A유저가B유저에게_팔로우(memberAId,memberCId);
		A유저가B유저에게_팔로우(memberCId,memberAId);

		A유저가B유저에게_팔로우(memberAId,memberDId);
		A유저가B유저에게_팔로우(memberDId,memberAId);

		RequestSpecification updateNickname = RestAssured.given(documentationSpec)
			.cookie("access-token",해당유저_JWT쿠키생성(memberAId))
			.log().all()
			.filter(document(
				"{class-name}/{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				responseFields(
					fieldWithPath("member_list").type(JsonFieldType.ARRAY).description("나랑 친구인 멤버 프로필 리스트"),
					fieldWithPath("member_list.[].member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("member_list.[].nickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
					fieldWithPath("member_list.[].groo").type(JsonFieldType.NUMBER).description("멤버의 남은 그루"),
					fieldWithPath("member_list.[].profile_image_url").type(JsonFieldType.STRING).description("멤버 프로필 사진 url(초기는 카카오 프로필)")
				)
			));

		//when & then
		var 결과 = updateNickname.when()
			.get("/member/follow/list")
			.then()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.jsonPath();

		assertThat(결과.getList("member_list")).hasSize(3);
	}
}
