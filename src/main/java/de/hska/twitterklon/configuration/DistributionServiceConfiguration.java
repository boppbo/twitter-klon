package de.hska.twitterklon.configuration;

import de.hska.twitterklon.api.transferobjects.PostDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> genericRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean("messaging")
    @Primary
    public RedisTemplate<String, PostDto> messagingRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, PostDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(PostDto.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListener messageListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener, new PatternTopic("timeline*"));

        return container;
    }
}
