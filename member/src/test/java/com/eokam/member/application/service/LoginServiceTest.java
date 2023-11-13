package com.eokam.member.application.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.service.JwtTokenProvider;
import com.eokam.member.infra.external.service.OauthProvider;
import com.eokam.member.infra.repository.MemberRepository;

public class LoginServiceTest extends BaseServiceTest{

	@InjectMocks
	LoginServiceImpl loginService;

	@Mock
	JwtTokenProvider jwtTokenProvider;

	@Mock
	OauthProvider oauthProvider;

	@Mock
	MemberRepository memberRepository;

	@Nested
	class loginWithToken_성공테스트{
	}
}
