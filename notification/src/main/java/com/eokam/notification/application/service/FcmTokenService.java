package com.eokam.notification.application.service;

import com.eokam.notification.application.dto.TokenDto;

public interface FcmTokenService {

	void register(TokenDto tokenDto);

	void delete(String jwt);

}
