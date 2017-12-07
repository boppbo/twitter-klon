package de.hska.twitterklon.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories("de.hska.twitterklon.redis")
public class DistributionServiceConfiguration {

    @Value("${distributionService.baseUrl}")
    private String distributionServiceBaseUrl;

    @Value("${distributionService.port}")
    private int distributionServicePort;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        final JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(distributionServiceBaseUrl);
        connectionFactory.setPort(distributionServicePort);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate() {
        return new RedisTemplate<byte[], byte[]>();
    }
}
