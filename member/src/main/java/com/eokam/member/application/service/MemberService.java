package com.eokam.member.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.SavingType;
import com.eokam.member.infra.dto.JwtMemberDto;

public interface MemberService {

	List<MemberDto> retrieveMemberProfile(List<Long> memberIdList);

	MemberDto retrieveMemberInfo(JwtMemberDto memberDto);

	MemberDto repayGroo(Long memberId, SavingType savingType, Integer groo);

	Boolean addBillCount(Long memberId);

	Boolean useBill(Long memberId);

	MemberDto updateNickname(JwtMemberDto jwtMemberDto, String nickname);

	MemberDto updateProfileImage(JwtMemberDto jwtMemberDto, MultipartFile multipartFile);

	Boolean checkDuplicateNickname(String nickname);

	MemberDto finishTest(JwtMemberDto jwtMemberDto,Integer groo);
}
