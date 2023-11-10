package com.eokam.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.SavingType;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.repository.MemberRepository;

public class MemberServiceTest extends BaseServiceTest {

	@InjectMocks
	private MemberServiceImpl memberService;

	@Mock
	private MemberRepository memberRepository;

	@Nested
	class retrieveMemberProfile_성공테스트 {
		@Test
		void 멤버프로필조회_테스트() {
			//given
			Long 멤버A_ID = 1L;
			String 멤버A_닉네임 = "꿈을꾸는문어";
			String 멤버A_프로필_URL = "test1.jpg";
			Member 멤버A_엔티티 = Member.builder().id(멤버A_ID).nickname(멤버A_닉네임).profileImageUrl(멤버A_프로필_URL).build();

			Long 멤버B_ID = 2L;
			String 멤버B_닉네임 = "나는문어";
			String 멤버B_프로필_URL = "test2.jpg";
			Member 멤버B_엔티티 = Member.builder().id(멤버B_ID).nickname(멤버B_닉네임).profileImageUrl(멤버B_프로필_URL).build();

			Long 멤버C_ID = 3L;
			String 멤버C_닉네임 = "몰랐어요";
			String 멤버C_프로필_URL = "test3.jpg";
			Member 멤버C_엔티티 = Member.builder().id(멤버C_ID).nickname(멤버C_닉네임).profileImageUrl(멤버C_프로필_URL).build();

			Long 멤버D_ID = 4L;
			String 멤버D_닉네임 = "내가벌레라는것을";
			String 멤버D_프로필_URL = "test4.jpg";
			Member 멤버D_엔티티 = Member.builder().id(멤버D_ID).nickname(멤버D_닉네임).profileImageUrl(멤버D_프로필_URL).build();

			List<Long> 멤버ID리스트 = new ArrayList<>();
			멤버ID리스트.add(멤버A_ID);
			멤버ID리스트.add(멤버B_ID);
			멤버ID리스트.add(멤버C_ID);
			멤버ID리스트.add(멤버D_ID);

			List<Member> 멤버_엔티티_리스트 = new ArrayList<>();

			멤버_엔티티_리스트.add(멤버A_엔티티);
			멤버_엔티티_리스트.add(멤버B_엔티티);
			멤버_엔티티_리스트.add(멤버C_엔티티);
			멤버_엔티티_리스트.add(멤버D_엔티티);

			given(memberRepository.findAllById(멤버ID리스트)).willReturn(멤버_엔티티_리스트);

			//when
			var 조회_결과 = memberService.retrieveMemberProfile(멤버ID리스트);

			//then
			verify(memberRepository).findAllById(멤버ID리스트);

			assertThat(조회_결과).hasSize(4);

			assertThat(조회_결과).extracting(MemberDto::getMemberId)
				.contains(멤버A_ID, 멤버B_ID, 멤버C_ID, 멤버D_ID);

			assertThat(조회_결과).extracting(MemberDto::getNickname)
				.contains(멤버A_닉네임, 멤버B_닉네임, 멤버C_닉네임, 멤버D_닉네임);

			assertThat(조회_결과).extracting(MemberDto::getProfileImageUrl)
				.contains(멤버A_프로필_URL, 멤버B_프로필_URL, 멤버C_프로필_URL, 멤버D_프로필_URL);


		}
	}

	@Nested
	class retrieveMemberProfile_실패테스트 {

		@Test
		void 멤버ID에_해당하는_멤버가없음() {
			//given
			Long 멤버A_ID = 1L;
			String 멤버A_닉네임 = "꿈을꾸는문어";
			String 멤버A_프로필_URL = "test1.jpg";
			Member 멤버A_엔티티 = Member.builder().id(멤버A_ID).nickname(멤버A_닉네임).profileImageUrl(멤버A_프로필_URL).build();

			Long 멤버B_ID = 2L;
			String 멤버B_닉네임 = "나는문어";
			String 멤버B_프로필_URL = "test2.jpg";
			Member 멤버B_엔티티 = Member.builder().id(멤버B_ID).nickname(멤버B_닉네임).profileImageUrl(멤버B_프로필_URL).build();

			Long 멤버C_ID = 3L;

			Long 멤버D_ID = 4L;
			String 멤버D_닉네임 = "내가벌레라는것을";
			String 멤버D_프로필_URL = "test4.jpg";
			Member 멤버D_엔티티 = Member.builder().id(멤버D_ID).nickname(멤버D_닉네임).profileImageUrl(멤버D_프로필_URL).build();

			List<Long> 멤버ID리스트 = new ArrayList<>();

			멤버ID리스트.add(멤버A_ID);
			멤버ID리스트.add(멤버B_ID);
			멤버ID리스트.add(멤버C_ID);
			멤버ID리스트.add(멤버D_ID);


			List<Member> 멤버_엔티티_리스트 = new ArrayList<>();

			멤버_엔티티_리스트.add(멤버A_엔티티);
			멤버_엔티티_리스트.add(멤버B_엔티티);
			멤버_엔티티_리스트.add(멤버D_엔티티);

			given(memberRepository.findAllById(멤버ID리스트)).willReturn(멤버_엔티티_리스트);

			//when & then
			assertThatThrownBy(()->{
				memberService.retrieveMemberProfile(멤버ID리스트);
			})
				.isExactlyInstanceOf(MemberNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.MEMBER_NOT_FOUND);

		}
	}

	@Nested
	class retrieveMemberInfo_성공테스트 {
		@Test
		void 내정보조회_테스트() {
			//given
			Long 멤버A_ID = 1L;
			String 멤버A_닉네임 = "꿈을꾸는문어";
			String 멤버A_프로필_URL = "test1.jpg";
			Member 멤버A_엔티티 = Member
				.builder().id(멤버A_ID)
				.nickname(멤버A_닉네임)
				.profileImageUrl(멤버A_프로필_URL)
				.id(1L)
				.build();

			JwtMemberDto JWT_멤버정보 = new JwtMemberDto(멤버A_ID);

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.of(멤버A_엔티티));

			//when
			var 조회_결과 = memberService.retrieveMemberInfo(JWT_멤버정보);

			//then
			verify(memberRepository).findById(멤버A_ID);
			assertThat(조회_결과).isNotNull();
			assertThat(조회_결과).extracting(MemberDto::getMemberId).isEqualTo(멤버A_ID);
			assertThat(조회_결과).extracting(MemberDto::getNickname).isEqualTo(멤버A_닉네임);
			assertThat(조회_결과).extracting(MemberDto::getProfileImageUrl).isEqualTo(멤버A_프로필_URL);

		}
	}

	@Nested
	class retrieveMemberInfo_실패테스트 {
		@Test
		void 가입안된JWT조회_테스트() {
			//given
			Long 멤버A_ID = 1L;
			String 멤버A_닉네임 = "꿈을꾸는문어";
			String 멤버A_프로필_URL = "test1.jpg";
			Member 멤버A_엔티티 = Member
				.builder().id(멤버A_ID)
				.nickname(멤버A_닉네임)
				.profileImageUrl(멤버A_프로필_URL)
				.id(1L)
				.build();

			JwtMemberDto JWT_멤버정보 = new JwtMemberDto(멤버A_ID);

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(()->{
				memberService.retrieveMemberInfo(JWT_멤버정보);
			}).isExactlyInstanceOf(MemberNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode",ErrorCode.MEMBER_NOT_FOUND);

		}
	}

	@Nested
	class repayGroo_성공테스트 {
		@Test
		void 그루갚기_고발_테스트() {
			//given
			Long 멤버A_ID = 1L;
			String 멤버A_닉네임 = "꿈을꾸는문어";
			String 멤버A_프로필_URL = "test1.jpg";
			Member 멤버A_엔티티 = Member
				.builder()
				.id(멤버A_ID)
				.nickname(멤버A_닉네임)
				.profileImageUrl(멤버A_프로필_URL)
				.build();

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.of(멤버A_엔티티));

			MemberDto 기대_결과 = MemberDto
				.builder()
				.nickname(멤버A_닉네임)
				.profileImageUrl(멤버A_프로필_URL)
				.memberId(멤버A_ID)
				.bill(멤버A_엔티티.getBill())
				.groo(멤버A_엔티티.getGroo()+300)
				.billCount(멤버A_엔티티.getBillCount())
				.build();


			//when
			var 조회_결과 = memberService.repayGroo(멤버A_ID, SavingType.ACCUSATION,300);

			//then
			verify(memberRepository).findById(멤버A_ID);
			assertThat(조회_결과).isNotNull();
			assertThat(조회_결과).usingRecursiveComparison().isEqualTo(조회_결과);

		}
	}

}
