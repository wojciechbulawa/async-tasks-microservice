package com.example.async.tasks.utils;

import io.restassured.RestAssured;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class RestAssuredPortSetupListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
        String port = testContext.getApplicationContext()
                .getEnvironment()
                .getProperty("local.server.port");

        RestAssured.port = Integer.parseInt(port);
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);
        RestAssured.reset();
    }
}
