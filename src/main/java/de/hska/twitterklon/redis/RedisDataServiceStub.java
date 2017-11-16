package de.hska.twitterklon.redis;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("DEV")
public class RedisDataServiceStub implements RedisDataService {

    @Override
    public String getUser(String userName) {
        return null;
    }
}



