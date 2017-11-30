package de.hska.twitterklon.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TimeLineController {

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String getPosts() {
        return "Hello World!";
    }
}
