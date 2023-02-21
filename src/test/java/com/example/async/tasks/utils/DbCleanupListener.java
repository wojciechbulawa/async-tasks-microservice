package com.example.async.tasks.utils;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

class DbCleanupListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
        getDbCleaner(testContext).truncate();
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);
        getDbCleaner(testContext).truncate();
    }

    private static DbCleaner getDbCleaner(TestContext testContext) {
        return testContext.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .getBean(DbCleaner.class);
    }
}
