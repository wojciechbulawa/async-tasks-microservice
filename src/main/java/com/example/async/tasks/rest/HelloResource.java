package com.example.async.tasks.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloResource {

    @GetMapping("/non-logged-in")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/logged-in")
    public String helloLoggedIn() {
        return "Hello logged-in user";
    }
}
