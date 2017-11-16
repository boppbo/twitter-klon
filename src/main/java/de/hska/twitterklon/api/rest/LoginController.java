package de.hska.twitterklon.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/login/{userId}")
public class LoginController {

    @PostMapping
    @ResponseBody
    public String login(@PathVariable("userId") String userId) {
        return UUID.randomUUID().toString() + userId;
    }
}
