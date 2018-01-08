package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTimelineMessageListenerImpl implements MessageListener{
    private final SimpMessagingTemplate stompTemplate;
    private final RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    private final RedisSerializer<PostDto> postSerializer = new Jackson2JsonRedisSerializer<>(PostDto.class);

    /** Creates a new instance of the redis timeline message listener impl.
     * @param stompTemplate The stomp template.
     */
    public RedisTimelineMessageListenerImpl(SimpMessagingTemplate stompTemplate) {
        this.stompTemplate = stompTemplate;
    }

    /**
     * Callback for processing received objects through Redis.
     *
     * @param message message
     * @param pattern pattern matching the channel (if specified) - can be null
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String destination = "/topic/" + stringSerializer.deserialize(message.getChannel());
        PostDto post = postSerializer.deserialize(message.getBody());

        this.stompTemplate.convertAndSend(destination, post);
    }
}
