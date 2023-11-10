package com.eokam.member.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.SavingType;
import com.eokam.member.infra.dto.JwtMemberDto;

public interface MemberService {

	public List<MemberDto> retrieveMemberProfile(List<Long> memberIdList);

	public MemberDto retrieveMemberInfo(JwtMemberDto memberDto);

	public MemberDto repayGroo(Long memberId, SavingType savingType, Integer groo);

	public Boolean addBillCount(Long memberId);

	public Boolean useBill(Long memberId);

	public MemberDto updateNickname(JwtMemberDto jwtMemberDto, String nickname);

	public MemberDto updateProfileImage(JwtMemberDto jwtMemberDto, MultipartFile multipartFile);

	public Boolean checkDuplicateNickname(String nickname);
}
