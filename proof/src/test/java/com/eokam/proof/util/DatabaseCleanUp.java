package com.eokam.proof.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DatabaseCleanUp {
	@PersistenceContext
	private EntityManager entityManager;
	private static final String[] tableNames = {"proof", "proof_image"};

	private void truncate() {
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
		for (String tableName : tableNames) {
			entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s RESTART IDENTITY;", tableName))
				.executeUpdate();
		}
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}

	@Transactional
	public void clear() {
		entityManager.clear();
		truncate();
	}
}