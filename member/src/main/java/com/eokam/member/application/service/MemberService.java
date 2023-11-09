package com.eokam.member.application.service;

import java.util.List;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.infra.dto.JwtMemberDto;

public interface MemberService {

	public List<MemberDto> retrieveMemberProfile(List<Long> memberIdList);

	public MemberDto retrieveMemberInfo(JwtMemberDto memberDto);

	public MemberDto updateMemberProfile(MemberDto memberDto);

	public MemberDto repayGroo(Integer groo);

	public void addBillCount();

	public void useBill();

	public Boolean checkDuplicateNickname(String nickname);
}
