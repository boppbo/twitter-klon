package de.hska.twitterklon.authentication;

import de.hska.twitterklon.redis.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final RedisDataService redisDataService;

    @Autowired
    public AuthenticationInterceptor(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if(request.getCookies() != null) {
            Optional<Cookie> authCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("auth")).findFirst();
            if (authCookie.isPresent()) {
                Cookie cookie = authCookie.get();
                if (cookie.getValue() != null) {
                    Optional<String> userName = redisDataService.getUserNameFromSession(cookie.getValue());
                    SessionInformation.setUser(userName.orElse(null), cookie.getValue());
                    if(SessionInformation.isUserSignedIn()) {
                        return true;
                    }
                }
            }
        }

        if(!isPublicURI(request) && !SessionInformation.isUserSignedIn()) {
            response.sendRedirect("/login");
            return false;
        }
        return isPublicURI(request);
    }

    private boolean isPublicURI(HttpServletRequest request) {
        return  request.getRequestURI().equals("/login") ||
                request.getRequestURI().equals("/api/v1/register") ||
                request.getRequestURI().equals("/api/v1/login");
    }
}
