package com.example.async.tasks.service.executor;


import lombok.NonNull;
import org.hibernate.validator.constraints.time.DurationMax;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "tasks.delay")
record SleepThreadProperties(
        @NonNull @DurationMax(millis = Integer.MAX_VALUE) @DurationUnit(ChronoUnit.MILLIS) Duration maxMillis,
        @NonNull @DurationMax(millis = Integer.MAX_VALUE) @DurationUnit(ChronoUnit.MILLIS) Duration minMillis) {
}
