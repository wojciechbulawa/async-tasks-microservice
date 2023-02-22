package com.example.async.tasks.utils;


import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "tests.await")
record TestAwaitProperties(
        @NonNull @DurationUnit(ChronoUnit.SECONDS) Duration atMostSec,
        @NonNull @DurationUnit(ChronoUnit.MILLIS) Duration pollDelayMs,
        @NonNull @DurationUnit(ChronoUnit.MILLIS) Duration pollIntervalMs) {
}
