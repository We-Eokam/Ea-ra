package com.eokam.cpoint.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eokam.cpoint.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
