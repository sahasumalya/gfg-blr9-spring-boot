package org.example.gfgblr9.config;

import lombok.extern.slf4j.Slf4j;
import org.example.gfgblr9.pubsub.RedisMessageListener;
import org.example.gfgblr9.pubsub.RedisMessageListener2;
import org.example.gfgblr9.pubsub.RedisMessagePublisher;
import org.example.gfgblr9.services.RedisDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Slf4j
@Configuration
public class DemoConfiguration {


     /*@Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }*/

    @Bean(name="redisConnectionFactory2")
    public LettuceConnectionFactory redisConnectionFactory2() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

    @Bean
    RedisTemplate<String, String> stringRedisTemplate2() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory2());
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();

    }

    @Bean
    RedisMessageListenerContainer container1(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    @Bean
    RedisMessageListenerContainer container2(RedisConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter2, new PatternTopic("xyz"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageListener subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(RedisMessageListener2 subscriber2) {
        return new MessageListenerAdapter(subscriber2, "onMessage");
    }

    @Bean
    RedisMessageListener subscriber() {
        return new RedisMessageListener();
    }

    @Bean
    RedisMessageListener2 subscriber2() {
        return new RedisMessageListener2();
    }


    @Bean
    RedisMessagePublisher publisher(RedisConnectionFactory connectionFactory) {
        return new RedisMessagePublisher(connectionFactory);
    }
}
