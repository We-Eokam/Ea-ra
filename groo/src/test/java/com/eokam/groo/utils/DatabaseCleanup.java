package com.eokam.groo.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DatabaseCleanup {
	@PersistenceContext
	private EntityManager entityManager;

	private void truncate() {
		String[] tableNames = {"groo_saving"};
		entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS = %d", 0)).executeUpdate();
		for (String tableName : tableNames) {
			entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
		}
		entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS = %d", 1)).executeUpdate();
	}

	@Transactional
	public void clear() {
		entityManager.clear();
		truncate();
	}
}


