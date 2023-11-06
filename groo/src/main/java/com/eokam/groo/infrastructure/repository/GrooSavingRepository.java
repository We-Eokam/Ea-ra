package com.eokam.groo.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;
import com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto;


public interface GrooSavingRepository extends JpaRepository<GrooSaving, Long> {

	@Query("select new com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto(date(gs.savedAt), "
		+ "sum(case when gs.savingType='PROOF' then gs.amount else 0 end), "
		+ "sum(case when gs.savingType='PROOF' then 1 else 0 end), "
		+ "sum(case when gs.savingType='ACCUSATION' then gs.amount else 0 end), "
		+ "sum(case when gs.savingType='ACCUSATION' then 1 else 0 end)) "
		+ "from GrooSaving gs "
		+ "where gs.memberId=:memberId and year(gs.savedAt) =:year and month(gs.savedAt)=:month group by date(gs.savedAt)")
	List<GrooDailySumAmountDto> getDailySumAndAmount(@Param("memberId") Long memberId, @Param("year") int year, @Param("month") int month);

	@Query("select new com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto("
		+ "sum(case when gs.savingType='PROOF' then gs.amount else 0 end), "
		+ "sum(case when gs.savingType='PROOF' then 1 else 0 end), "
		+ "sum(case when gs.savingType='ACCUSATION' then gs.amount else 0 end), "
		+ "sum(case when gs.savingType='ACCUSATION' then 1 else 0 end)) "
		+ "from GrooSaving gs "
		+ "where gs.memberId=:memberId and year(gs.savedAt) =:year and month(gs.savedAt)=:month")
	GrooMonthSumAmountDto getSumAndAmountByMonth(@Param("memberId") Long memberId, @Param("year") int year, @Param("month") int month);

}
