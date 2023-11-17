package com.eokam.cpoint.application.service;

import java.util.List;

import com.eokam.cpoint.application.dto.CompanyDetailDto;
import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.presentation.dto.MemberDto;

public interface CompanyService {

	List<CompanyDto> retrieveCompanyList(MemberDto memberDto, ActivityType activityType);

	CompanyDetailDto retrieveCompanyDetail(MemberDto memberDto, Long companyId);

	CompanyDto connectCompany(MemberDto memberDto, Long companyId);

	CompanyDto disconnectCompany(MemberDto memberDto, Long companyId);

}
