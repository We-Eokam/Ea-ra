package com.eokam.cpoint.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.CompanyPolicy;

@Repository
public interface CompanyPolicyRepository extends JpaRepository<CompanyPolicy, Long> {

	List<CompanyPolicy> findCompanyPoliciesByCompanyId(Long companyId);

	List<CompanyPolicy> findCompanyPoliciesByActivityType(ActivityType activityType);

}
