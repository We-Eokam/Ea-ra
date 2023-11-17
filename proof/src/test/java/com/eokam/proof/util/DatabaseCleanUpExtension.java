package com.eokam.proof.util;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanUpExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		DatabaseCleanUp databaseCleanUp = getDatabaseCleanup(context);
		databaseCleanUp.clear();
	}

	public DatabaseCleanUp getDatabaseCleanup(ExtensionContext context) {
		return SpringExtension.getApplicationContext(context)
			.getBean(DatabaseCleanUp.class);
	}
}
