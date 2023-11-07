package com.eokam.cpoint.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.cpoint.domain.CompanyConnect;

public interface CompanyConnectRepository extends JpaRepository<CompanyConnect, Long> {

	Optional<CompanyConnect> findCompanyConnectByMemberIdAAndCompanyId(Long memberId, Long companyId);

}
