package com.eokam.notification.application.token.service;

import org.springframework.stereotype.Service;

import com.eokam.notification.application.token.dto.TokenDto;
import com.eokam.notification.domain.token.entity.Token;
import com.eokam.notification.domain.token.repository.FcmTokenRepository;
import com.eokam.notification.infrastructure.util.ParseJwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmTokenTokenServiceImpl implements FcmTokenService {

	private final FcmTokenRepository fcmTokenRepository;

	@Override
	public void register(TokenDto of) {
		fcmTokenRepository.save(Token.from(of));
	}

	@Override
	public void delete(String jwt) {
		fcmTokenRepository.delete(ParseJwtUtil.parseMemberId(jwt));
	}

	@Override
	public TokenDto getToken(Long memberId) {
		Token token = fcmTokenRepository.findTokenByMemberId(memberId)
			.orElseThrow();
		return TokenDto.from(token);
	}

}
