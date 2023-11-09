package com.eokam.member.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	@Transactional(readOnly = true)
	public List<MemberDto> retrieveMemberProfile(List<Long> memberIdList) {
		return null;
	}

	@Override
	public MemberDto retrieveMemberInfo(JwtMemberDto memberDto) {
		return null;
	}

	@Override
	public MemberDto updateMemberProfile(MemberDto memberDto) {
		return null;
	}

	@Override
	public MemberDto repayGroo(Integer groo) {
		return null;
	}

	@Override
	public void addBillCount() {

	}

	@Override
	public void useBill() {

	}

	@Override
	public Boolean checkDuplicateNickname(String nickname) {
		return null;
	}
}
