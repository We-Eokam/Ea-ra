package com.eokam.notification.application.token.service;

import com.eokam.notification.application.token.dto.TokenDto;

public interface FcmTokenService {

	void register(TokenDto tokenDto);

	void delete(String jwt);

	TokenDto getToken(Long memberId);

}
