package com.eokam.member.util;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class DatabaseCleanup {
	@PersistenceContext
	private EntityManager entityManager;

	private void truncate() {
		String[] tableNames = {"member","member_follow"};
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

