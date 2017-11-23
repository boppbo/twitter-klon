package de.hska.twitterklon.api.rest;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import de.hska.twitterklon.api.exceptions.LoginFailedException;
import de.hska.twitterklon.api.transferobjects.LoginTO;
import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class LoginController {

    @Value("${appConfig.sessionTimeout}")
    private int sessionTTL;

    private final RedisDataService redisDataService;

    @Autowired
    public LoginController(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @RequestMapping(path = "login", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void login(@Valid @RequestBody LoginTO loginTO, HttpServletResponse response) {
        Optional<String> uuid = redisDataService.createSession(loginTO.getUserId(), loginTO.getPassword(), sessionTTL);

        if(!uuid.isPresent()) {
            throw new LoginFailedException();
        }
        createSessionCookie(response, uuid.get());
    }

    private void createSessionCookie(HttpServletResponse response, String uuid) {
        Cookie cookie = new Cookie("auth", uuid);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody LoginTO loginTO) {
        return UUID.randomUUID().toString();
    }
}
