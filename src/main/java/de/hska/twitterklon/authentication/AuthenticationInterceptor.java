package de.hska.twitterklon.authentication;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisTemplate template;

    private final RedisDataService redisDataService;

    public AuthenticationInterceptor(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional<Cookie> authCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("authCookie")).findFirst();
        if(authCookie.isPresent()) {
            Cookie cookie = authCookie.get();
            if(cookie.getValue() != null) {
                Optional<String> userName = redisDataService.getUserNameFromSession(cookie.getValue());
                userName.ifPresent(s -> SessionInformation.setUser(s, cookie.getValue()));
            }
        }
        return true;
    }
}
