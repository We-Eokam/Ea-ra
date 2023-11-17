package com.eokam.cpoint.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.cpoint.application.dto.CpointDto;
import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.Cpoint;
import com.eokam.cpoint.global.ErrorCode;
import com.eokam.cpoint.global.exception.BusinessException;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.infra.CpointRepository;
import com.eokam.cpoint.presentation.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpointServiceImpl implements CpointService {

	private final CpointRepository cpointRepository;
	private final CompanyRepository companyRepository;

	@Override
	@Transactional(readOnly = true)
	public Integer retrieveCpoint(MemberDto member) {
		return cpointRepository.sumCpointByMemberIdWhereCreatedAtInThisMonth(member.getMemberId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<CpointSummaryDto> retrieveCpointSummary(MemberDto member) {
		return cpointRepository.findCpointSummaryByMemberId(member.getMemberId());
	}

	@Override
	@Transactional
	public CpointDto saveCpoint(CpointDto cpointDto) {
		Company company = companyRepository
			.findById(cpointDto.getCompanyId())
			.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		Cpoint cpoint = cpointRepository.save(Cpoint.of(cpointDto, company));
		return CpointDto.from(cpoint);
	}
}
