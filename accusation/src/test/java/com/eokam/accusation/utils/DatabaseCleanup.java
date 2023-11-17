package com.eokam.accusation.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DatabaseCleanup {
	@PersistenceContext
	private EntityManager entityManager;

	private void truncate() {
		String[] tableNames = {"accusation", "accusation_image"};
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
		for (String tableName : tableNames) {
			entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
		}
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}

	@Transactional
	public void clear() {
		entityManager.clear();
		truncate();
	}
}
