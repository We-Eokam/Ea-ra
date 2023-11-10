package com.eokam.member.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.JwtParseException;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.dto.KakaoAccessTokenResponse;
import com.eokam.member.infra.dto.KakaoMemberResponse;
import com.eokam.member.infra.external.service.JwtTokenProvider;
import com.eokam.member.infra.external.service.OauthProvider;
import com.eokam.member.infra.external.service.TokenBlackList;
import com.eokam.member.infra.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

	private final JwtTokenProvider tokenProvider;

	private final OauthProvider oauthProvider;

	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public MemberDto loginWithToken(String accessToken) {
		tokenProvider.validateToken(accessToken);
		JwtMemberDto jwtMemberDto = tokenProvider.parseJwtToken(accessToken);
		Member member = memberRepository.findById(jwtMemberDto.getMemberId())
			.orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		return MemberDto.from(member);
	}

	@Override
	@Transactional
	public MemberDto login(String authorizationCode) {
		KakaoAccessTokenResponse accessTokenResponse =
			oauthProvider.getAccessToken(authorizationCode);

		KakaoMemberResponse kakaoMemberResponse =
			oauthProvider.getMemberInfo(accessTokenResponse.getAccessToken());

		Optional<Member> member = memberRepository.findMemberBySocialId(kakaoMemberResponse.getId());

		if(member.isPresent()){
			return MemberDto.from(member.get());
		}

		Member newMember = Member.builder()
			.nickname(kakaoMemberResponse.getKakaoAccount().getNickname())
			.profileImageFileName("초기프로필.jpg")
			.profileImageUrl(kakaoMemberResponse.getKakaoAccount().getProfileImageUrl())
			.socialId(kakaoMemberResponse.getId())
			.build();

		memberRepository.save(newMember);
		return MemberDto.from(newMember);
	}

	@Override
	@Transactional
	public void logout(String accessToken) {
		tokenProvider.invalidateToken(accessToken);
	}
}
