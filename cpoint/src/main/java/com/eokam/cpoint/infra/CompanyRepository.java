package com.eokam.cpoint.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("select new com.eokam.cpoint.application.dto.CompanyDto(c.id,c.name,"
		+ " CASE WHEN cc.memberId IS NULL THEN false ELSE true END)"
		+ " FROM Company c"
		+ " LEFT JOIN CompanyConnect cc"
		+ " ON c.id = cc.company.id"
		+ " AND cc.memberId = :memberId"
		+ " INNER JOIN CompanyPolicy cp"
		+ " ON cp.company.id = c.id"
		+ " AND cp.activityType = :activityType"
		+ " ORDER BY c.name")
	public List<CompanyDto> findCompaniesByActivityTypeAndMemberId(ActivityType activityType, Long memberId);

	@Query("select new com.eokam.cpoint.application.dto.CompanyDto(c.id,c.name,"
		+ " CASE WHEN cc.memberId IS NULL THEN false ELSE true END)"
		+ " FROM Company c"
		+ " LEFT JOIN CompanyConnect  cc "
		+ " ON c.id  = cc.company.id"
		+ " AND cc.memberId = :memberId"
		+ " WHERE c.id = :companyId")
	public Optional<CompanyDto> findCompanyByCompanyIdAndMemberId(Long companyId, Long memberId);

	public Optional<Company> findCompanyById(Long companyId);
}
