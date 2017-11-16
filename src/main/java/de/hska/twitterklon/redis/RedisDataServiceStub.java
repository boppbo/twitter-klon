package de.hska.twitterklon.redis;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("DEV")
@Primary
public class RedisDataServiceStub implements RedisDataService {
    @Override
    public String getUser(String userName) {
        return null;
    }
}
