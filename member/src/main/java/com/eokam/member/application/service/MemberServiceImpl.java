 package com.eokam.member.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.SavingType;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.global.exception.NicknameAlreadyExistException;
import com.eokam.member.global.exception.NoBillException;
import com.eokam.member.global.exception.TestException;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.S3FileDetail;
import com.eokam.member.infra.external.service.S3Service;
import com.eokam.member.infra.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	private final S3Service s3Service;

	@Override
	@Transactional(readOnly = true)
	public List<MemberDto> retrieveMemberProfile(List<Long> memberIdList) {
		List<Member> memberList = memberRepository.findAllById(memberIdList);
		if(memberList.size() != memberIdList.size()){
			throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
		}
		return memberList.stream().map(member->MemberDto.from(member)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public MemberDto retrieveMemberInfo(JwtMemberDto memberDto) {
		Member member = memberRepository.findById(memberDto.getMemberId())
			.orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
		return MemberDto.from(member);
	}

	@Override
	@Transactional
	public MemberDto repayGroo(Long memberId, SavingType savingType, Integer groo) {
		Member member = memberRepository
			.findById(memberId).orElseThrow(()->new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		member.repayGroo(savingType,groo);

		return MemberDto.from(member);
	}

	@Override
	@Transactional
	public Boolean addBillCount(Long memberId) {
		Member member = memberRepository
			.findById(memberId).orElseThrow(()->new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		return member.addBillCount();
	}

	@Override
	@Transactional
	public Boolean useBill(Long memberId) {
		Member member = memberRepository
			.findById(memberId).orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		if(!member.useBill()){
			throw new NoBillException(ErrorCode.BILL_NOT_ENOUGH);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean checkDuplicateNickname(String nickname) {
		if(!memberRepository.findMemberByNickname(nickname).isEmpty()){
			throw new NicknameAlreadyExistException(ErrorCode.NICKNAME_ALREADY_EXIST);
		}
		return true;
	}

	@Override
	@Transactional
	public MemberDto updateNickname(JwtMemberDto jwtMemberDto, String nickname) {
		Member member = memberRepository
			.findById(jwtMemberDto.getMemberId()).orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		if(checkDuplicateNickname(nickname)) member.changeNickname(nickname);
		return MemberDto.from(member);
	}

	@Override
	@Transactional
	public MemberDto updateProfileImage(JwtMemberDto jwtMemberDto, MultipartFile multipartFile) {
		Member member = memberRepository
			.findById(jwtMemberDto.getMemberId()).orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
		S3FileDetail s3FileDetail = s3Service.save(multipartFile);
		member.changeProfileImage(s3FileDetail.getFileName(),s3FileDetail.getUrl());
		return MemberDto.from(member);
	}
	@Override
	@Transactional
	public MemberDto finishTest(JwtMemberDto jwtMemberDto,Integer groo) {
		Member member = memberRepository
			.findById(jwtMemberDto.getMemberId()).orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
		if(!member.finishTest(groo)){
			throw new TestException(ErrorCode.TEST_ALREADY_DONE);
		}
		return MemberDto.from(member);
	}


}
