package com.example.async.tasks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "auth")
public record InMemoryUsers(List<InMemoryUser> users) {

    public InMemoryUser findUserWithRole(String role) {
        return users.stream()
                .filter((inMemoryUser -> inMemoryUser.getRoles().contains(role)))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No user configured for tests"));
    }
}
