package com.example.async.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AsyncTasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncTasksApplication.class, args);
    }

}
