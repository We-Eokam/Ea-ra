package com.eokam.cpoint.infra.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyConnect;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.domain.Cpoint;
import com.eokam.cpoint.domain.Store;
import com.eokam.cpoint.infra.CompanyConnectRepository;
import com.eokam.cpoint.infra.CompanyPolicyRepository;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.infra.CpointRepository;
import com.eokam.cpoint.infra.StoreRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public abstract class CommonRepositoryTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	protected CpointRepository cpointRepository;

	@Autowired
	protected CompanyRepository companyRepository;

	@Autowired
	protected CompanyPolicyRepository companyPolicyRepository;

	@Autowired
	protected StoreRepository storeRepository;

	@Autowired
	protected CompanyConnectRepository companyConnectRepository;

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

	protected Long 단일매장_생성(final Store store) {
		return storeRepository.save(store).getId();
	}

	protected void 여러매장_생성(final Store... stores) {
		var storesToSave = List.of(stores);
		storeRepository.saveAll(storesToSave);
	}

	protected Long 단일연동_생성(final CompanyConnect companyConnect) {
		return companyConnectRepository.save(companyConnect).getId();
	}

	protected void 여러연동_생성(final CompanyConnect... companyConnects) {
		var companyConntectsToSave = List.of(companyConnects);
		companyConnectRepository.saveAll(companyConntectsToSave);
	}
}
