package com.eokam.member.application.service;

import com.eokam.member.application.dto.MemberDto;

public interface LoginService {

	public MemberDto login(String authorizationCode);

	public MemberDto loginWithToken(String accessToken);

	public void logout(String accessToken);

}
