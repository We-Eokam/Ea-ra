package com.eokam.cpoint.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.eokam.cpoint.application.common.BaseServiceTest;
import com.eokam.cpoint.application.dto.CpointDto;
import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.Cpoint;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.infra.CpointRepository;
import com.eokam.cpoint.presentation.dto.MemberDto;
import com.eokam.cpoint.presentation.dto.MemberRole;

public class CpointServiceTest extends BaseServiceTest {

	@InjectMocks
	private CpointServiceImpl cpointService;

	@Mock
	private CpointRepository cpointRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	void 탄소중립포인트_조회_테스트() {
		//given
		final MemberDto memberDto = MemberDto
			.builder()
			.memberId(1L)
			.memberRole(MemberRole.MEMBER)
			.build();

		given(cpointRepository.sumCpointByMemberIdWhereCreatedAtInThisMonth(memberDto.getMemberId())).willReturn(
			100000);

		//when
		Integer cpoint = cpointService.retrieveCpoint(memberDto);

		//then
		verify(cpointRepository).sumCpointByMemberIdWhereCreatedAtInThisMonth(1L);
		assertThat(cpoint).isEqualTo(100000);
	}

	@Test
	void 탄소중립포인트_적립_테스트() {
		//given
		final MemberDto memberDto = MemberDto
			.builder()
			.memberId(1L)
			.memberRole(MemberRole.ADMIN)
			.build();

		final Company company = Company.builder().id(1L).name("스타벅스").build();

		final CpointDto cpointDto = CpointDto.builder()
			.member(memberDto)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.point(1000)
			.companyId(1L)
			.build();

		final Cpoint cpoint = Cpoint.of(cpointDto, company);

		given(companyRepository.findById(cpointDto.getCompanyId())).willReturn(
			Optional.of(company));

		given(
			cpointRepository.save(
				argThat(cp -> cp.getActivityType().equals(ActivityType.EMISSION_FREE_CAR)))).willReturn(
			cpoint);

		//when
		cpointService.saveCpoint(cpointDto);

		//then
		verify(companyRepository).findById(1L);
		verify(cpointRepository).save(argThat(cp -> cp.getActivityType().equals(ActivityType.EMISSION_FREE_CAR)));
	}

	@Test
	void 탄소중립포인트활동_요약_테스트() {
		//given
		final MemberDto memberDto = MemberDto
			.builder()
			.memberId(1L)
			.memberRole(MemberRole.MEMBER)
			.build();

		CpointSummaryDto recycleProductSummary = new CpointSummaryDto(ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS,
			10000L);
		CpointSummaryDto useContainerSummary = new CpointSummaryDto(ActivityType.MULTI_USE_CONTAINER, 1000L);
		CpointSummaryDto discardedPhoneSummary = new CpointSummaryDto(ActivityType.DISCARDED_PHONE, 5000L);
		CpointSummaryDto ecoFriendlySummary = new CpointSummaryDto(ActivityType.ECO_FRIENDLY_PRODUCTS, 3000L);

		List<CpointSummaryDto> cpointSummaryDtoList = new ArrayList<>();

		cpointSummaryDtoList.add(recycleProductSummary);
		cpointSummaryDtoList.add(useContainerSummary);
		cpointSummaryDtoList.add(discardedPhoneSummary);
		cpointSummaryDtoList.add(ecoFriendlySummary);

		given(cpointRepository.findCpointSummaryByMemberId(memberDto.getMemberId())).willReturn(cpointSummaryDtoList);

		//when
		List<CpointSummaryDto> cpointSummaryDtos = cpointService.retrieveCpointSummary(memberDto);
		verify(cpointRepository).findCpointSummaryByMemberId(memberDto.getMemberId());

		assertThat(cpointSummaryDtos).hasSize(4);

		assertThat(cpointSummaryDtos)
			.extracting(CpointSummaryDto::getActivityType)
			.contains(ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS,
				ActivityType.MULTI_USE_CONTAINER,
				ActivityType.DISCARDED_PHONE,
				ActivityType.ECO_FRIENDLY_PRODUCTS);
	}
}
