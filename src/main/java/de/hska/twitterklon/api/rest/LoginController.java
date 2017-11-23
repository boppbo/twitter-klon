package de.hska.twitterklon.api.rest;

import java.util.UUID;

import javax.validation.Valid;

import de.hska.twitterklon.api.transferobjects.LoginTO;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/")
public class LoginController {

    private final RedisDataService redisDataService;

    @Autowired
    public LoginController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @RequestMapping(path = "login/{userId}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(@PathVariable("userId") String userId, @Valid @RequestBody LoginTO loginTO) {
        return UUID.randomUUID().toString() + userId;
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String register(@PathVariable("userId") String userId, @Valid @RequestBody LoginTO loginTO) {
        return UUID.randomUUID().toString() + userId;
    }
}
