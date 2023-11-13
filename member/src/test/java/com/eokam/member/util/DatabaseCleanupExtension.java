package com.eokam.member.util;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanupExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		DatabaseCleanup databaseCleanup = getDatabaseCleanup(context);
		databaseCleanup.clear();
	}

	public DatabaseCleanup getDatabaseCleanup(ExtensionContext context) {
		return SpringExtension.getApplicationContext(context)
			.getBean(DatabaseCleanup.class);
	}
}
