package com.eokam.cpoint.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.domain.Cpoint;

@Repository
public interface CpointRepository extends JpaRepository<Cpoint, Long> {

	Integer sumCpointByMemberIdWhereCreatedAtInThisMonth(Long memberId);

	List<CpointSummaryDto> findCpointSummaryByMemberId(Long memberId);

}
