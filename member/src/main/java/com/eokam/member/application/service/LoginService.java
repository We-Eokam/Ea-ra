package com.eokam.member.application.service;

import com.eokam.member.application.dto.MemberDto;

public interface LoginService {

	public String login(String authorizationCode);

	public MemberDto loginWithToken(String accessToken);

	public void logout(String accessToken);

}
