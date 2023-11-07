package com.eokam.cpoint.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.cpoint.application.dto.CompanyDetailDto;
import com.eokam.cpoint.application.dto.CompanyDto;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;

	private final CompanyPolicyRepository companyPolicyRepository;

	private final CompanyConnectRepository companyConnectRepository;

	@Override
	@Transactional(readOnly = true)
	public List<CompanyDto> retrieveCompanyList(MemberDto memberDto, ActivityType activityType) {
		return companyRepository.findCompaniesByActivityTypeAndMemberId(activityType, memberDto.getMemberId());
	}

	@Override
	@Transactional(readOnly = true)
	public CompanyDetailDto retrieveCompanyDetail(MemberDto memberDto, Long companyId) {
		CompanyDto companyDto = companyRepository.findCompanyByCompanyIdAndMemberId(companyId, memberDto.getMemberId())
			.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));
		List<CompanyPolicy> companyPolicies = companyPolicyRepository.findCompanyPoliciesByCompanyId(companyId);
		return CompanyDetailDto.of(companyDto, companyPolicies);
	}

	@Override
	@Transactional
	public CompanyDto connectCompany(MemberDto memberDto, Long companyId) {

		Optional<CompanyConnect> alreadyConnected =
			companyConnectRepository.findCompanyConnectByMemberIdAndCompanyId(memberDto.getMemberId(), companyId);

		if (alreadyConnected.isPresent()) {
			throw new BusinessException(ErrorCode.COMPANY_ALREADY_CONNECTED);
		}

		Company company = companyRepository.findCompanyById(companyId)
			.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		CompanyConnect companyConnect =
			CompanyConnect.builder().company(company).memberId(memberDto.getMemberId()).build();

		companyConnectRepository.save(companyConnect);
		return CompanyDto.of(company, true);
	}

	@Override
	public CompanyDto disconnectCompany(MemberDto memberDto, Long companyId) {

		CompanyConnect alreadyConnected = companyConnectRepository
			.findCompanyConnectByMemberIdAndCompanyId(memberDto.getMemberId(), companyId)
			.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_CONNECTED));

		Company company = companyRepository.findCompanyById(companyId)
			.orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		companyConnectRepository.delete(alreadyConnected);
		return CompanyDto.of(company, false);
	}
}
