package com.eokam.member.presentation.common;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.member.config.RestDocsConfig;
import com.eokam.member.domain.Member;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.service.JwtTokenProvider;
import com.eokam.member.infra.repository.MemberRepository;
import com.eokam.member.util.DatabaseCleanupExtension;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ExtendWith({DatabaseCleanupExtension.class})
@ActiveProfiles("test")
public class BasicControllerTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public static final String BASE_URL = "http://localhost";

	@LocalServerPort
	private int port;

	protected RequestSpecification documentationSpec;

	@BeforeEach
	public void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
		RestAssured.port = port;
		RestAssured.baseURI = BASE_URL;

		Filter documentConfiguration = documentationConfiguration(restDocumentation)
			.operationPreprocessors()
			.withRequestDefaults(prettyPrint())
			.withResponseDefaults(prettyPrint());

		this.documentationSpec = new RequestSpecBuilder()
			.addFilter(documentConfiguration)
			.setPort(port)
			.setBaseUri(BASE_URL)
			.build();
	}

	protected Long 닉네임이어캄인_프로필사진이범고래인유저생성(){
		Member member = Member.builder()
			.nickname("어캄")
			.profileImageUrl("\"https://eokam-eara.s3.ap-northeast-2.amazonaws.com/%EB%B2%94%EA%B3%A0%EB%9E%98.jpg")
			.profileImageFileName("%EB%B2%94%EA%B3%A0%EB%9E%98.jpg")
			.build();
		return memberRepository.save(member).getId();
	}

	protected Long 닉네임이나는문어인_프로필사진이펭귄인유저생성(){
		Member member = Member.builder()
			.nickname("나는문어")
			.profileImageUrl("https://eokam-eara.s3.ap-northeast-2.amazonaws.com/%ED%8E%AD%EA%B7%84.jpg")
			.profileImageFileName("%ED%8E%AD%EA%B7%84.jpg")
			.build();
		return memberRepository.save(member).getId();
	}

	protected Long 닉네임이꿈을꾸는문어인_프로필사진이펭귄인유저생성(){
		Member member = Member.builder()
			.nickname("꿈을꾸는문어")
			.profileImageUrl("https://eokam-eara.s3.ap-northeast-2.amazonaws.com/%ED%8E%AD%EA%B7%84.jpg")
			.profileImageFileName("%ED%8E%AD%EA%B7%84.jpg")
			.build();
		return memberRepository.save(member).getId();
	}

	protected Long 닉네임이나는감자인_프로필사진이범고래인유저생성(){
		Member member = Member.builder()
			.nickname("나는감자")
			.profileImageUrl("https://eokam-eara.s3.ap-northeast-2.amazonaws.com/%EB%B2%94%EA%B3%A0%EB%9E%98.jpg")
			.profileImageFileName("%EB%B2%94%EA%B3%A0%EB%9E%98.jpg")
			.build();
		return memberRepository.save(member).getId();
	}

	protected void 해당유저_고소장1개증가(Long memberId){
		Member member = memberRepository.findById(memberId).get();
		member.addBillCount();
		member.addBillCount();
		member.addBillCount();
		memberRepository.save(member);
	}

	protected void 해당유저_테스트완료(Long memberId){
		Member member = memberRepository.findById(memberId).get();
		member.finishTest(0);
		memberRepository.save(member);
	}

	protected String 해당유저_JWT쿠키생성(Long memberId){
		return jwtTokenProvider.provideToken(new JwtMemberDto(memberId));
	}
}
