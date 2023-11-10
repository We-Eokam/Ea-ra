package com.eokam.member.infra.external.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.LoginException;

@Component
public class MapTokenBlackList implements TokenBlackList{

	private final ConcurrentHashMap<String,Boolean> blackList = new ConcurrentHashMap<>();

	@Override
	public void invalidate(String accessToken) {
		if(!blackList.containsKey(accessToken)) throw new LoginException(ErrorCode.TOKEN_ALREADY_LOGOUT);
		blackList.put(accessToken,false);
	}

	@Override
	public Boolean validate(String accessToken) {
		return !blackList.containsKey(accessToken);
	}
}
