package com.eokam.cpoint.infra.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.domain.Cpoint;
import com.eokam.cpoint.infra.CompanyPolicyRepository;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.infra.CpointRepository;

@EnableJpaAuditing
@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public abstract class CommonRepositoryTest {

	@Autowired
	protected CpointRepository cpointRepository;

	@Autowired
	protected CompanyRepository companyRepository;

	@Autowired
	protected CompanyPolicyRepository companyPolicyRepository;

	protected Long 단일회사_생성(final Company company) {
		return companyRepository.save(company).getId();
	}

	protected void 여러회사_생성(final Company... companies) {
		var companiesToSave = List.of(companies);
		companyRepository.saveAll(companiesToSave);
	}

	protected Long 단일탄소중립포인트적립내역_생성(final Cpoint cpoint) {
		return cpointRepository.save(cpoint).getId();
	}

	protected void 여러탄소중립포인트적립내역_생성(final Cpoint... cpoints) {
		var cpointsToSave = List.of(cpoints);
		cpointRepository.saveAll(cpointsToSave);
	}

	protected Long 단일회사정책_생성(final CompanyPolicy companyPolicy) {
		return companyPolicyRepository.save(companyPolicy).getId();
	}

	protected void 여러회사정책_생성(final CompanyPolicy... companyPolicies) {
		var companyPoliciesToSave = List.of(companyPolicies);
		companyPolicyRepository.saveAll(companyPoliciesToSave);
	}
}
