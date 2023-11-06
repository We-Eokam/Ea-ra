package com.eokam.cpoint.application.service;

import java.util.List;

import com.eokam.cpoint.application.dto.CpointDto;
import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.presentation.dto.MemberDto;

public interface CpointService {

	Integer retrieveCpoint(MemberDto member);

	List<CpointSummaryDto> retrieveCpointSummary(MemberDto member);

	CpointDto saveCpoint(CpointDto cpointDto);
}
