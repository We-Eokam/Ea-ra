package com.eokam.member.infra.external.service;

public interface TokenBlackList {

	void invalidate(String accessToken);

	Boolean validate(String accessToken);

}
