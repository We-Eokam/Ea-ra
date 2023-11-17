package com.eokam.cpoint.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.domain.Cpoint;

@Repository
public interface CpointRepository extends JpaRepository<Cpoint, Long> {
	@Query("SELECT COALESCE(SUM(cp.point),0)"
		+ " FROM Cpoint cp"
		+ " WHERE YEAR(cp.createdAt) = YEAR(CURRENT_DATE)"
		+ " AND MONTH(cp.createdAt) = MONTH(CURRENT_DATE)"
		+ " AND cp.memberId = :memberId ")
	Integer sumCpointByMemberIdWhereCreatedAtInThisMonth(Long memberId);

	@Query("SELECT new com.eokam.cpoint.application.dto.CpointSummaryDto(cp.activityType,SUM(cp.point)) FROM Cpoint cp "
		+ " WHERE cp.memberId = :memberId"
		+ " GROUP BY cp.activityType ")
	List<CpointSummaryDto> findCpointSummaryByMemberId(Long memberId);

}
