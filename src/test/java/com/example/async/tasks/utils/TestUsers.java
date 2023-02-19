package com.example.async.tasks.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUsers {

    public Credentials getAdmin() {
        return new Credentials("admin", "admin");
    }

    public Credentials getUser() {
        return new Credentials("user", "user");
    }
}
