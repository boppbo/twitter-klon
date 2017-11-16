package de.hska.twitterklon.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisDataServiceImpl implements RedisDataService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisDataServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getUser(String userName) {
        return null;
    }
}
