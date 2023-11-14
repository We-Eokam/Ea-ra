 package com.eokam.member.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.MemberFollow;
import com.eokam.member.domain.SavingType;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.BusinessException;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.global.exception.NicknameAlreadyExistException;
import com.eokam.member.global.exception.NoBillException;
import com.eokam.member.global.exception.TestException;
import com.eokam.member.infra.dto.FollowStatus;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.S3FileDetail;
import com.eokam.member.infra.external.service.S3Service;
import com.eokam.member.infra.repository.MemberFollowRepository;
import com.eokam.member.infra.repository.MemberRepository;
import com.eokam.member.presentation.dto.MemberProfileListReponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	private final MemberFollowRepository memberFollowRepository;

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
			throw new BusinessException(ErrorCode.BILL_NOT_ENOUGH);
		}
		return true;
	}

	@Override
	@Transactional
	public Boolean useBillByTarget(Long memberId,Long targetId) {
		Member member = memberRepository
			.findById(memberId).orElseThrow(()-> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		memberRepository
			.findById(targetId).orElseThrow(()->new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		if(!checkFollowStatus(memberId,targetId).equals(FollowStatus.FRIEND)){
			throw new BusinessException(ErrorCode.THEY_NOT_FOLLOW);
		}

		if(!member.useBill()){
			throw new BusinessException(ErrorCode.BILL_NOT_ENOUGH);
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

	@Override
	@Transactional(readOnly = true)
	public FollowStatus checkFollowStatus(Long memberA,Long memberB) {
		memberRepository.findById(memberA).orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
		memberRepository.findById(memberB).orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
		Optional<MemberFollow> aFollowB = memberFollowRepository
			.findMemberFollowByRequestorIdAndReceiverId(memberA,memberB);

		Optional<MemberFollow> bFollowA = memberFollowRepository
			.findMemberFollowByRequestorIdAndReceiverId(memberB,memberA);

		if(aFollowB.isPresent() && bFollowA.isPresent()){
			return FollowStatus.FRIEND;
		}
		else if(aFollowB.isPresent() && bFollowA.isEmpty()){
			return FollowStatus.REQUEST;
		}
		else if(aFollowB.isEmpty() && bFollowA.isPresent()){
			return FollowStatus.ACCEPT;
		}
		else{
			return FollowStatus.NOTHING;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public FollowStatus followMember(Long requestorId, Long receiverId) {

		Optional<MemberFollow> memberFollow =
			memberFollowRepository.findMemberFollowByRequestorIdAndReceiverId(requestorId,receiverId);

		if(memberFollow.isPresent()){
			throw new BusinessException(ErrorCode.ALREADY_FOLLOW);
		}

		Member requestor = memberRepository.findById(requestorId)
			.orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		Member receiver = memberRepository.findById(receiverId)
			.orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		MemberFollow newMemberFollow = MemberFollow.builder().requestor(requestor).receiver(receiver).build();
		memberFollowRepository.save(newMemberFollow);
		return checkFollowStatus(requestorId,receiverId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MemberDto> retrieveFriendMemberList(Long memberId) {
		List<Long> memberIdList = memberFollowRepository
			.retrieveFollowedMemberByMemberId(memberId);

		List<Member> memberList = memberRepository.findAllById(memberIdList);

		return memberList.stream().map(member -> MemberDto.from(member)).toList();
	}

	@Override
	@Transactional
	public FollowStatus denyOrCancelFollowRequest(Long requestorId, Long receiverId) {
		Optional<MemberFollow> memberFollow =
			memberFollowRepository.findMemberFollowByRequestorIdAndReceiverId(requestorId,receiverId);
		if(memberFollow.isPresent()) memberFollowRepository.delete(memberFollow.get());

		Optional<MemberFollow> friendFollow = memberFollowRepository.findMemberFollowByRequestorIdAndReceiverId(receiverId,requestorId);

		if(friendFollow.isPresent()) memberFollowRepository.delete(friendFollow.get());

		return checkFollowStatus(requestorId,receiverId);
	}

	@Override
	@Transactional
	public FollowStatus acceptFollowRequest(Long requestorId, Long receiverId) {
		memberFollowRepository.findMemberFollowByRequestorIdAndReceiverId(requestorId,receiverId)
			.orElseThrow(()->new BusinessException(ErrorCode.NO_FOLLOW_REQUEST));

		Member requestor = memberRepository.findById(requestorId)
			.orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		Member receiver = memberRepository.findById(receiverId)
			.orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		Optional<MemberFollow> memberFollow = memberFollowRepository
			.findMemberFollowByRequestorIdAndReceiverId(receiverId,requestorId);

		if(memberFollow.isPresent()) throw new BusinessException(ErrorCode.ALREADY_FOLLOW);

		//팔로우 수락이므로 requestor receiver가 바뀌어야함
		MemberFollow newMemberFollow = MemberFollow.builder().requestor(receiver).receiver(requestor).build();
		memberFollowRepository.save(newMemberFollow);
		return checkFollowStatus(requestorId,receiverId);
	}

	@Override
	public List<MemberDto> retrieveAllMember() {
		return memberRepository.findAll().stream().map(member -> MemberDto.from(member)).toList();
	}
}
