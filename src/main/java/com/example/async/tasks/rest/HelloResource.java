package com.example.async.tasks.rest;

import com.example.async.tasks.dto.HelloMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/hello")
public class HelloResource {

    @GetMapping(path = "/non-logged-in", produces = APPLICATION_JSON_VALUE)
    public HelloMessage hello() {
        return new HelloMessage("Hello");
    }

    @GetMapping(path = "/logged-in", produces = APPLICATION_JSON_VALUE)
    public HelloMessage helloLoggedIn() {
        return new HelloMessage("Hello logged-in user");
    }
}
