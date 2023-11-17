package com.eokam.cpoint.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.cpoint.application.dto.CompanyDetailDto;
import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.application.service.CompanyService;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.presentation.annotation.JwtMember;
import com.eokam.cpoint.presentation.dto.CompanyDetailResponse;
import com.eokam.cpoint.presentation.dto.CompanyListResponse;
import com.eokam.cpoint.presentation.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cpoint/company")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping()
	public ResponseEntity<CompanyListResponse> retrieveCompanyList(@JwtMember MemberDto memberDto,
		@RequestParam("category") ActivityType activityType) {
		List<CompanyDto> companyList = companyService.retrieveCompanyList(memberDto, activityType);
		return ResponseEntity.ok(CompanyListResponse.from(companyList));
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<CompanyDetailResponse> retrieveCompanyDetail(@JwtMember MemberDto memberDto,
		@PathVariable("companyId") Long companyId) {
		CompanyDetailDto companyDetailDto = companyService.retrieveCompanyDetail(memberDto, companyId);
		return ResponseEntity.ok(CompanyDetailResponse.from(companyDetailDto));
	}

	@PostMapping("/{companyId}/connect")
	public ResponseEntity<CompanyDetailResponse> connectCompany(@JwtMember MemberDto memberDto,
		@PathVariable("companyId") Long companyId) {
		CompanyDto companyDto = companyService.connectCompany(memberDto, companyId);
		return ResponseEntity
			.created(URI.create("/cpoint/company/" + companyId))
			.body(CompanyDetailResponse.from(companyDto));
	}

	@DeleteMapping("/{companyId}/connect")
	public ResponseEntity<CompanyDetailResponse> disconnectCompany(@JwtMember MemberDto memberDto,
		@PathVariable("companyId") Long companyId) {
		CompanyDto companyDto = companyService.disconnectCompany(memberDto, companyId);
		return ResponseEntity
			.ok()
			.body(CompanyDetailResponse.from(companyDto));
	}

}
