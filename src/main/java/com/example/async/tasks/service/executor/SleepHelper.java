package com.example.async.tasks.service.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SleepHelper {

    private final Random random;
    private final int min;
    private final int max;

    public SleepHelper(SleepThreadProperties props) {
        this.random = new Random();
        this.min = (int) props.minMillis().toMillis();
        this.max = (int) props.maxMillis().toMillis();
    }

    public void sleep() throws InterruptedException {
        int millis = random.nextInt(min, max);
        log.debug("Sleep for {} millis", millis);
        Thread.sleep(millis);
    }
}
