package com.eokam.member.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.application.service.MemberService;
import com.eokam.member.infra.dto.FollowStatus;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.presentation.annotation.JwtUser;
import com.eokam.member.presentation.dto.BillRequest;
import com.eokam.member.presentation.dto.MemberDetailResponse;
import com.eokam.member.presentation.dto.MemberFollowRequest;
import com.eokam.member.presentation.dto.MemberFollowStatusResponse;
import com.eokam.member.presentation.dto.MemberNicknameUpdateRequest;
import com.eokam.member.presentation.dto.MemberProfileListReponse;
import com.eokam.member.presentation.dto.MemberProfileResponse;
import com.eokam.member.presentation.dto.MemberTestDoneRequest;
import com.eokam.member.presentation.dto.RepayGrooRequest;
import com.eokam.member.presentation.dto.UseBillRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/detail")
	public ResponseEntity<MemberDetailResponse> retrieveMemberInfo(@RequestParam Long memberId){
		MemberDetailResponse memberDetailResponse = MemberDetailResponse.from(
			memberService.retrieveMemberInfo(new JwtMemberDto(memberId))
		);
		return ResponseEntity.ok(memberDetailResponse);
	}

	@GetMapping
	public ResponseEntity<MemberProfileListReponse> retrieveMembers(
		@RequestParam(name="memberId",required = true) List<Long> memberIdList){
		MemberProfileListReponse memberProfileListReponse =
			MemberProfileListReponse.builder().memberList(
			memberService.retrieveMemberProfile(memberIdList)
				.stream().map(memberDto -> MemberProfileResponse.from(memberDto)).toList()
			).build();
		return ResponseEntity.ok(memberProfileListReponse);
	}

	@GetMapping("/list")
	public ResponseEntity<MemberProfileListReponse> retrieveMembers(){
		MemberProfileListReponse memberProfileListReponse =
			MemberProfileListReponse.builder().memberList(
				memberService.retrieveAllMember()
					.stream().map(memberDto -> MemberProfileResponse.from(memberDto)).toList()
			).build();
		return ResponseEntity.ok(memberProfileListReponse);
	}

	@PostMapping("/groo")
	public ResponseEntity<MemberDetailResponse> saveGroo(@RequestBody @Valid RepayGrooRequest repayGrooRequest){
		MemberDetailResponse memberDetailResponse =
			MemberDetailResponse.from(memberService.repayGroo(
				repayGrooRequest.getMemberId(),repayGrooRequest.getSavingType(),repayGrooRequest.getGroo()));
		return ResponseEntity
			.created(URI.create("/detail?memberId="+memberDetailResponse.getMemberId()))
			.body(memberDetailResponse);
	}

	@DeleteMapping("/accusation")
	public ResponseEntity<?> useBill(@RequestBody @Valid BillRequest billRequest){
		memberService.useBill(billRequest.getMemberId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/accusation")
	public ResponseEntity<?> useBillByTarget(@RequestBody @Valid UseBillRequest useBillRequest){
		memberService.useBillByTarget(useBillRequest.getMemberId(), useBillRequest.getTargetId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/accusation/count")
	public ResponseEntity<?> addBillCount(@RequestBody @Valid BillRequest billRequest){
		memberService.addBillCount(billRequest.getMemberId());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/nickname/{nickname}")
	public ResponseEntity<?> checkDuplicateNickname(@PathVariable(name="nickname",required = true) String nickname){
		memberService.checkDuplicateNickname(nickname);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/nickname")
	public ResponseEntity<?> updateNickname(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestBody MemberNicknameUpdateRequest memberNicknameUpdateRequest){
		MemberDetailResponse memberDetailResponse =
			MemberDetailResponse.from(memberService.updateNickname(jwtMemberDto,memberNicknameUpdateRequest.getNickname()));
		return ResponseEntity.ok().body(memberDetailResponse);
	}

	@PutMapping("/test")
	public ResponseEntity<MemberDetailResponse> testDone(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestBody MemberTestDoneRequest memberTestDoneRequest){
		MemberDetailResponse memberDetailResponse =
			MemberDetailResponse.from(memberService.finishTest(jwtMemberDto,memberTestDoneRequest.getGroo()));
		return ResponseEntity.ok(memberDetailResponse);
	}

	@PostMapping(value="/profileImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemberDetailResponse> updateProfileImage(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestPart(value="profile_image",required = true) MultipartFile multipartFile){
		MemberDetailResponse memberDetailResponse =
			MemberDetailResponse.from(memberService.updateProfileImage(jwtMemberDto,multipartFile));
		return ResponseEntity.ok(memberDetailResponse);
	}

	@GetMapping("/follow")
	public ResponseEntity<MemberFollowStatusResponse> checkFollow(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestParam("memberId")Long memberId){
		FollowStatus followStatus =
			memberService.checkFollowStatus(jwtMemberDto.getMemberId(),memberId);
		return ResponseEntity.ok(MemberFollowStatusResponse
			.builder().followStatus(followStatus).memberId(memberId).build());
	}

	@PostMapping("/follow")
	public ResponseEntity<MemberFollowStatusResponse> followMember(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestBody MemberFollowRequest memberFollowRequest){
		FollowStatus followStatus =
			memberService.followMember(jwtMemberDto.getMemberId(),memberFollowRequest.getTargetId());
		return ResponseEntity.ok(MemberFollowStatusResponse
			.builder().followStatus(followStatus).memberId(memberFollowRequest.getTargetId()).build());
	}

	@GetMapping("/follow/list")
	public ResponseEntity<MemberProfileListReponse> retreiveFriendList(@JwtUser JwtMemberDto jwtMemberDto){
		MemberProfileListReponse memberProfileListReponse =
			MemberProfileListReponse.builder().memberList(
				memberService.retrieveFriendMemberList(jwtMemberDto.getMemberId())
					.stream().map(memberDto -> MemberProfileResponse.from(memberDto)).toList()
			).build();
		return ResponseEntity.ok(memberProfileListReponse);
	}

	@DeleteMapping("/follow")
	public ResponseEntity<MemberFollowStatusResponse> cancelFollow(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestParam("targetId")Long targetId){
		FollowStatus followStatus = memberService.denyOrCancelFollowRequest(jwtMemberDto.getMemberId(),targetId);
		return ResponseEntity.ok(MemberFollowStatusResponse.builder()
			.memberId(targetId).followStatus(followStatus).build()
		);
	}

	@PostMapping("/follow/accept")
	public ResponseEntity<MemberFollowStatusResponse> acceptFollowRequest(@JwtUser JwtMemberDto jwtMemberDto,
		@RequestBody MemberFollowRequest memberFollowRequest){
		FollowStatus followStatus = memberService.acceptFollowRequest(memberFollowRequest.getTargetId(), jwtMemberDto.getMemberId());
		return ResponseEntity.ok(MemberFollowStatusResponse.builder()
			.memberId(memberFollowRequest.getTargetId()).followStatus(followStatus).build()
		);
	}

}
