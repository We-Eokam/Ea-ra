package com.eokam.cpoint.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.eokam.cpoint.application.common.BaseServiceTest;
import com.eokam.cpoint.application.dto.CompanyDetailDto;
import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.application.dto.CompanyPolicyDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyConnect;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.global.ErrorCode;
import com.eokam.cpoint.global.exception.BusinessException;
import com.eokam.cpoint.infra.CompanyConnectRepository;
import com.eokam.cpoint.infra.CompanyPolicyRepository;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.presentation.dto.MemberDto;
import com.eokam.cpoint.presentation.dto.MemberRole;

public class CompanyServiceTest extends BaseServiceTest {

	@InjectMocks
	CompanyServiceImpl companyService;

	@Mock
	CompanyRepository companyRepository;

	@Mock
	CompanyPolicyRepository companyPolicyRepository;

	@Mock
	CompanyConnectRepository companyConnectRepository;

	@Nested
	class retrieveCompanyList_테스트 {
		@Test
		void 회사목록조회_테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			List<CompanyDto> 회사목록 = new ArrayList<>();

			CompanyDto 스타벅스_연동됨 = new CompanyDto(1L, "스타벅스", true);
			CompanyDto 메가커피_연동안됨 = new CompanyDto(2L, "메가커피", false);
			CompanyDto 폴바셋_연동됨 = new CompanyDto(3L, "폴바셋", true);
			CompanyDto 더벤티_연동됨 = new CompanyDto(4L, "더벤티", true);

			회사목록.add(스타벅스_연동됨);
			회사목록.add(메가커피_연동안됨);
			회사목록.add(폴바셋_연동됨);
			회사목록.add(더벤티_연동됨);

			given(companyRepository.findCompaniesByActivityTypeAndMemberId(ActivityType.TUMBLER,
				memberDto.getMemberId())).willReturn(
				회사목록);

			//when
			List<CompanyDto> 조회결과 = companyService.retrieveCompanyList(memberDto, ActivityType.TUMBLER);

			//then
			verify(companyRepository).findCompaniesByActivityTypeAndMemberId(ActivityType.TUMBLER,
				memberDto.getMemberId());

			assertThat(조회결과).hasSize(4);
			assertThat(조회결과)
				.filteredOn(companyDto -> companyDto.getIsConnect().equals(true))
				.extracting(CompanyDto::getCompanyName)
				.containsOnly("스타벅스", "폴바셋", "더벤티");

		}
	}

	@Nested
	class retrieveCompanyDetail_테스트 {

		@Test
		void 회사상세조회_테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			List<CompanyDto> 회사목록 = new ArrayList<>();

			Company 메가커피 = Company.builder().name("메가커피").build();

			Optional<CompanyDto> 메가커피_연동안됨 = Optional.of(CompanyDto.of(메가커피, false));

			given(companyRepository.findCompanyByCompanyIdAndMemberId(메가커피_연동안됨.get().getCompanyId(),
				memberDto.getMemberId())).willReturn(메가커피_연동안됨);

			List<CompanyPolicy> 정책목록 = new ArrayList<>();
			var 메가커피_텀블러 = CompanyPolicy
				.builder()
				.company(메가커피)
				.activityType(ActivityType.TUMBLER)
				.target("모든 점포")
				.build();

			var 메가커피_전자영수증 = CompanyPolicy
				.builder()
				.company(메가커피)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("모든 점포")
				.build();

			정책목록.add(메가커피_전자영수증);
			정책목록.add(메가커피_텀블러);

			given(companyPolicyRepository.findCompanyPoliciesByCompanyId(메가커피.getId())).willReturn(정책목록);

			//when
			CompanyDetailDto 조회결과 = companyService.retrieveCompanyDetail(memberDto, 메가커피.getId());

			//then
			verify(companyRepository).findCompanyByCompanyIdAndMemberId(메가커피.getId(), memberDto.getMemberId());
			verify(companyPolicyRepository).findCompanyPoliciesByCompanyId(메가커피.getId());

			assertThat(조회결과).isNotNull();
			assertThat(조회결과.getCompanyId()).isEqualTo(메가커피.getId());
			assertThat(조회결과.getCompanyName()).isEqualTo(메가커피.getName());
			assertThat(조회결과.getIsConnect()).isEqualTo(false);
			assertThat(조회결과.getCompanyPolicies())
				.hasSize(2)
				.usingRecursiveFieldByFieldElementComparator()
				.contains(CompanyPolicyDto.from(메가커피_전자영수증))
				.contains(CompanyPolicyDto.from(메가커피_텀블러));

		}

		@Test
		void 회사상세조회_회사없을때_테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			List<CompanyDto> 회사목록 = new ArrayList<>();

			Company 메가커피 = Company.builder().name("메가커피").build();

			Optional<CompanyDto> 메가커피_연동안됨 = Optional.of(CompanyDto.of(메가커피, false));

			given(companyRepository.findCompanyByCompanyIdAndMemberId(메가커피_연동안됨.get().getCompanyId(),
				memberDto.getMemberId())).willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> {
				companyService.retrieveCompanyDetail(memberDto, 메가커피.getId());
			})
				.isExactlyInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_NOT_FOUND);

		}
	}

	@Nested
	class 회사연동_테스트 {

		@Test
		void 연동테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			Company 메가커피 = Company.builder().id(1L).name("메가커피").build();

			given(companyRepository.findCompanyById(메가커피.getId())).willReturn(Optional.of(메가커피));

			CompanyConnect 메가커피_연동 =
				CompanyConnect
					.builder()
					.company(메가커피)
					.memberId(memberDto.getMemberId())
					.build();

			given(companyConnectRepository
				.findCompanyConnectByMemberIdAAndCompanyId(memberDto.getMemberId(), 메가커피.getId()))
				.willReturn(Optional.empty());

			given(companyConnectRepository
				.save(argThat(cc -> cc.getCompany().equals(메가커피))))
				.willReturn(메가커피_연동);

			//when
			var 생성결과 = companyService.connectCompany(memberDto, 메가커피.getId());

			//then
			verify(companyRepository).findCompanyById(메가커피.getId());
			verify(companyConnectRepository)
				.findCompanyConnectByMemberIdAAndCompanyId(memberDto.getMemberId(), 메가커피.getId());
			verify(companyConnectRepository).save(argThat(cc -> cc.getCompany().getName().equals("메가커피")));

			assertThat(생성결과.getCompanyId()).isEqualTo(메가커피.getId());
			assertThat(생성결과.getCompanyName()).isEqualTo(메가커피.getName());
			assertThat(생성결과.getIsConnect()).isTrue();
		}

		@Test
		void 연동실패_테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			Company 메가커피 = Company.builder().id(1L).name("메가커피").build();

			CompanyConnect 메가커피_연동 =
				CompanyConnect
					.builder()
					.company(메가커피)
					.memberId(memberDto.getMemberId())
					.build();

			given(companyConnectRepository
				.findCompanyConnectByMemberIdAAndCompanyId(memberDto.getMemberId(), 메가커피.getId()))
				.willReturn(Optional.of(메가커피_연동));

			//when & then
			assertThatThrownBy(() -> companyService.connectCompany(memberDto, 메가커피.getId()))
				.isExactlyInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_ALREADY_CONNECTED);
		}

	}

	@Nested
	class 연동해제_테스트 {

		@Test
		void 연동해제테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			Company 메가커피 = Company.builder().id(1L).name("메가커피").build();

			given(companyRepository.findCompanyById(메가커피.getId())).willReturn(Optional.of(메가커피));

			CompanyConnect 메가커피_연동 =
				CompanyConnect
					.builder()
					.company(메가커피)
					.memberId(memberDto.getMemberId())
					.build();

			given(companyConnectRepository
				.findCompanyConnectByMemberIdAAndCompanyId(memberDto.getMemberId(), 메가커피.getId()))
				.willReturn(Optional.of(메가커피_연동));

			//when
			var 삭제결과 = companyService.disconnectCompany(memberDto, 메가커피.getId());

			//then
			verify(companyRepository).findCompanyById(메가커피.getId());
			verify(companyConnectRepository)
				.findCompanyConnectByMemberIdAAndCompanyId(memberDto.getMemberId(), 메가커피.getId());
			verify(companyConnectRepository).delete(argThat(cc -> cc.getCompany().getName().equals("메가커피")));

			assertThat(삭제결과.getCompanyId()).isEqualTo(메가커피.getId());
			assertThat(삭제결과.getCompanyName()).isEqualTo(메가커피.getName());
			assertThat(삭제결과.getIsConnect()).isFalse();
		}

		@Test
		void 연동해제_실패테스트() {
			//given
			final MemberDto memberDto = MemberDto
				.builder()
				.memberId(1L)
				.memberRole(MemberRole.MEMBER)
				.build();

			Company 메가커피 = Company.builder().id(1L).name("메가커피").build();

			given(companyConnectRepository
				.findCompanyConnectByMemberIdAAndCompanyId(memberDto.getMemberId(), 메가커피.getId()))
				.willReturn(Optional.empty());

			//when & then
			assertThatThrownBy(() -> companyService.disconnectCompany(memberDto, 메가커피.getId()))
				.isExactlyInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_NOT_CONNECTED);

		}
	}
}
