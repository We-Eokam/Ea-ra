package com.eokam.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.domain.Member;
import com.eokam.member.domain.SavingType;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.AmazonS3Exception;
import com.eokam.member.global.exception.MemberNotFoundException;
import com.eokam.member.global.exception.NicknameAlreadyExistException;
import com.eokam.member.global.exception.NoBillException;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.S3FileDetail;
import com.eokam.member.infra.external.service.S3Service;
import com.eokam.member.infra.repository.MemberRepository;

public class MemberServiceTest extends BaseServiceTest {

	@InjectMocks
	private MemberServiceImpl memberService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private S3Service s3Service;

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

		@Test
		void 그루갚기_인증_테스트() {
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
			var 조회_결과 = memberService.repayGroo(멤버A_ID, SavingType.PROOF,300);

			//then
			verify(memberRepository).findById(멤버A_ID);
			assertThat(조회_결과).isNotNull();
			assertThat(조회_결과).usingRecursiveComparison().isEqualTo(조회_결과);

		}
	}
	@Nested
	class repayGroo_실패테스트 {
		@Test
		void 멤버없음_테스트() {
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

			given(memberRepository.findById(멤버A_ID)).willThrow(MemberNotFoundException.class);



			//when & then
			assertThatThrownBy(()->{
				memberService.repayGroo(멤버A_ID, SavingType.ACCUSATION,300);
			})
				.isExactlyInstanceOf(MemberNotFoundException.class);
		}
	}

	@Nested
	class addBillCount_성공테스트 {
		@Test
		void 고소장카운트_2개미만() {
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

			//when
			memberService.addBillCount(멤버A_ID);

			//then
			verify(memberRepository).findById(멤버A_ID);
			assertThat(멤버A_엔티티.getBillCount()).isEqualTo(1);
		}


		@Test
		void 고소장카운트_2개() {
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
			memberService.addBillCount(멤버A_ID);
			memberService.addBillCount(멤버A_ID);

			//when
			memberService.addBillCount(멤버A_ID);

			//then
			assertThat(멤버A_엔티티.getBill()).isEqualTo(1);
			assertThat(멤버A_엔티티.getBillCount()).isEqualTo(0);
		}
	}

	@Nested
	class addBillCount_실패테스트 {
		@Test
		void 멤버없음() {
			//given
			Long 멤버A_ID = 1L;

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(()->{
				memberService.addBillCount(멤버A_ID);
				verify(memberRepository).findById(멤버A_ID);
			})
				.isExactlyInstanceOf(MemberNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode",ErrorCode.MEMBER_NOT_FOUND);
		}

	}

	@Nested
	class useBill_성공테스트 {
		@Test
		void 고소장사용() {
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

			memberService.addBillCount(멤버A_ID);
			memberService.addBillCount(멤버A_ID);
			memberService.addBillCount(멤버A_ID);
			assertThat(멤버A_엔티티.getBill()).isEqualTo(1);

			//when
			memberService.useBill(멤버A_ID);

			//then
			verify(memberRepository,atLeastOnce()).findById(멤버A_ID);
			assertThat(멤버A_엔티티.getBill()).isEqualTo(0);
		}
	}

	@Nested
	class useBill_실패테스트 {
		@Test
		void 고소장이_0개() {
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
			assertThat(멤버A_엔티티.getBill()).isEqualTo(0);

			//when & then
			assertThatThrownBy(()->{
				memberService.useBill(멤버A_ID);
			})
				.isExactlyInstanceOf(NoBillException.class)
				.hasFieldOrPropertyWithValue("errorCode",ErrorCode.BILL_NOT_ENOUGH);
		}
	}

	@Nested
	class checkDuplicateNickname_성공테스트 {
		@Test
		void 닉네임_중복_아님() {
			//given
			String targetNickname = "코딩못하는개발자";
			given(memberRepository.findMemberByNickname(targetNickname))
				.willReturn(Optional.empty());

			//when
			var 조회_결과 = memberService.checkDuplicateNickname(targetNickname);

			//then
			verify(memberRepository).findMemberByNickname(targetNickname);
			assertThat(조회_결과).isTrue();
		}
	}

	@Nested
	class checkDuplicateNickname_실패테스트 {
		@Test
		void 닉네임_중복임() {
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
			given(memberRepository.findMemberByNickname(멤버A_닉네임))
				.willReturn(Optional.of(멤버A_엔티티));

			//when & then
			assertThatThrownBy(()->{
				memberService.checkDuplicateNickname(멤버A_닉네임);
			})
				.isExactlyInstanceOf(NicknameAlreadyExistException.class)
				.hasFieldOrPropertyWithValue("errorCode",ErrorCode.NICKNAME_ALREADY_EXIST);
		}
	}

	@Nested
	class updateNickname_성공테스트 {
		@Test
		void 닉네임_변경	() {
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

			String 변경할_닉네임 = "나는문어";

			JwtMemberDto jwt멤버정보 = new JwtMemberDto(멤버A_ID);

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.of(멤버A_엔티티));
			given(memberRepository.findMemberByNickname(변경할_닉네임)).willReturn(Optional.empty());

			//when
			var 변경_결과 = memberService.updateNickname(jwt멤버정보,변경할_닉네임);

			//then
			verify(memberRepository).findById(멤버A_ID);
			assertThat(멤버A_엔티티.getNickname()).isEqualTo(변경할_닉네임);
			assertThat(변경_결과)
				.usingRecursiveComparison().isEqualTo(MemberDto.from(멤버A_엔티티));
		}
	}

	@Nested
	class updateNickname_실패테스트 {
		@Test
		void 닉네임_이미_있음	() {
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

			String 변경할_닉네임 = "나는문어";

			Long 멤버B_ID = 2L;
			String 멤버B_닉네임 = 변경할_닉네임;
			String 멤버B_프로필_URL = "test2.jpg";
			Member 멤버B_엔티티 = Member
				.builder()
				.id(멤버B_ID)
				.nickname(멤버B_닉네임)
				.profileImageUrl(멤버B_프로필_URL)
				.build();

			JwtMemberDto jwt멤버정보 = new JwtMemberDto(멤버A_ID);

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.of(멤버A_엔티티));
			given(memberRepository.findMemberByNickname(변경할_닉네임)).willReturn(Optional.of(멤버B_엔티티));

			//when & then
			assertThatThrownBy(()->{
				memberService.updateNickname(jwt멤버정보,변경할_닉네임);
			})
				.isExactlyInstanceOf(NicknameAlreadyExistException.class)
				.hasFieldOrPropertyWithValue("errorCode",ErrorCode.NICKNAME_ALREADY_EXIST);
		}
	}

	@Nested
	class updateProfileImage_성공테스트 {
		@Test
		void 프로필이미지_변경() throws IOException {
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

			ClassPathResource resource = new ClassPathResource("static/eora.png");


			String 변경할_파일이름 = "eora";
			String 변경할_파일이름_확장자 = "eora.png";
			String S3에_저장된_url = "http://test.com/eora.png";

			JwtMemberDto jwt멤버정보 = new JwtMemberDto(멤버A_ID);

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.of(멤버A_엔티티));

			MockMultipartFile file =
				new MockMultipartFile(
					변경할_파일이름,
					변경할_파일이름_확장자,
					MediaType.IMAGE_PNG_VALUE,
					resource.getContentAsByteArray()
				);

			S3FileDetail s3에_저장된_파일 = S3FileDetail
					.builder()
					.fileName(변경할_파일이름_확장자)
					.url(S3에_저장된_url)
					.build();

			given(s3Service.save(file)).willReturn(s3에_저장된_파일);

			//when
			var 변경_결과 = memberService.updateProfileImage(jwt멤버정보,file);

			//then
			verify(memberRepository).findById(멤버A_ID);
			assertThat(멤버A_엔티티.getProfileImageUrl()).isEqualTo(s3에_저장된_파일.getUrl());
			assertThat(멤버A_엔티티.getProfileImageFileName()).isEqualTo(s3에_저장된_파일.getFileName());
			assertThat(변경_결과)
				.usingRecursiveComparison().isEqualTo(MemberDto.from(멤버A_엔티티));

		}
	}
	@Nested
	class updateProfileImage_실패테스트 {
		@Test
		void s3업로드과정에서_오류() throws IOException {
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

			ClassPathResource resource = new ClassPathResource("static/eora.png");


			String 변경할_파일이름 = "eora";
			String 변경할_파일이름_확장자 = "eora.png";
			String S3에_저장된_url = "http://test.com/eora.png";

			JwtMemberDto jwt멤버정보 = new JwtMemberDto(멤버A_ID);

			given(memberRepository.findById(멤버A_ID)).willReturn(Optional.of(멤버A_엔티티));

			MockMultipartFile file =
				new MockMultipartFile(
					변경할_파일이름,
					변경할_파일이름_확장자,
					MediaType.IMAGE_PNG_VALUE,
					resource.getContentAsByteArray()
				);

			given(s3Service.save(file)).willThrow(AmazonS3Exception.class);

			//when & then
			assertThatThrownBy(()->{
				memberService.updateProfileImage(jwt멤버정보,file);
				})
				.isExactlyInstanceOf(AmazonS3Exception.class);
		}
	}

}
