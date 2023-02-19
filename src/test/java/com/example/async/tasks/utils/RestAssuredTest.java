package com.example.async.tasks.utils;

import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TestExecutionListeners(value = RestAssuredPortSetupListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestAssuredTest {
}
