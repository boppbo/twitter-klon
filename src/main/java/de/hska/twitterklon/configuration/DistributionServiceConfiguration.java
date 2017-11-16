package de.hska.twitterklon.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class DistributionServiceConfiguration {

    @Value("${distributionService.baseUrl}")
    private String distributionServiceBaseurl;

    @Value("${distributionService.port}")
    private int distributionServicePort;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(5);
        poolConfig.setTestOnBorrow(true);

        final JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(distributionServiceBaseurl);
        connectionFactory.setPort(distributionServicePort);
        connectionFactory.afterPropertiesSet();
        connectionFactory.setUsePool(true);
        connectionFactory.setPoolConfig(poolConfig);
        return connectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        final StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory());
        return redisTemplate;
    }
}
