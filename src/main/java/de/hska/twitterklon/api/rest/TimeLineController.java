package de.hska.twitterklon.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TimeLineController {

    @GetMapping
    @ResponseBody
    public String placeholder() {
        return "Hello World!";
    }
}
