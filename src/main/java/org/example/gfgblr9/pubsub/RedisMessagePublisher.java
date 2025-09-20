package org.example.gfgblr9.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

public class RedisMessagePublisher {

    private final StringRedisTemplate redisTemplate;
    private final ChannelTopic topic;

    public RedisMessagePublisher(RedisConnectionFactory connectionFactory) {
        this.redisTemplate = new StringRedisTemplate(connectionFactory);
        this.topic = new ChannelTopic("xyz");
    }

    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
