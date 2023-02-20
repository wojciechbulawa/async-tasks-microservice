package com.example.async.tasks.rest;

import com.example.async.tasks.dto.HelloMessage;
import com.example.async.tasks.message.MsgSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/hello")
@RequiredArgsConstructor
public class HelloResource {

    private final MsgSender msgSender;

    @GetMapping(path = "/non-logged-in", produces = APPLICATION_JSON_VALUE)
    public HelloMessage hello() {
        return new HelloMessage("Hello");
    }

    @GetMapping(path = "/logged-in", produces = APPLICATION_JSON_VALUE)
    public HelloMessage helloLoggedIn() {
        HelloMessage msg = new HelloMessage("Hello logged-in user");
        msgSender.send(msg);
        return msg;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
    public HelloMessage helloAdmin() {
        HelloMessage msg = new HelloMessage("Hello admin");
        msgSender.sendAsAdmin(msg);
        return msg;
    }
}
