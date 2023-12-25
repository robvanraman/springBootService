package com.raman.demo.config;


import com.raman.demo.service.KeyChangedListener;
import com.raman.demo.service.KeyChangedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Slf4j
public class RedisConfg {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
//
//        return new LettuceConnectionFactory(configuration);
//    }
//
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//
//        RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();
//
//        return RedisCacheManager.builder(redisConnectionFactory())
//                .cacheDefaults(cacheConfig)
//                .withCacheConfiguration("tutorials", myDefaultCacheConfig(Duration.ofMinutes(5)))
//                .withCacheConfiguration("tutorial", myDefaultCacheConfig(Duration.ofMinutes(1)))
//                .build();
//
//    }
//
//
//    private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
//        return RedisCacheConfiguration
//                .defaultCacheConfig()
//                .entryTtl(duration)
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisHost);
        jedisConFactory.setPort(redisPort);
        return jedisConFactory;

        //RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
       // return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        //template.setDefaultSerializer(new StringRedisSerializer()); // set here
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, KeyChangedListener keyChangedListener) {
//        log.info("redisMessageListenerContainer fired");
//        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
//        listenerContainer.setConnectionFactory(connectionFactory);
//        //.addMessageListener(maxWaitTimeExpiryListener, new PatternTopic("__key*__:task_with_max_wait_time__*"));
//        listenerContainer.addMessageListener(keyChangedListener, new PatternTopic("__key*__:mykey"));
//        listenerContainer.setErrorHandler( e -> log.error("Error in redisMessageListenerContainer", e));
//        return listenerContainer;
//    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), new PatternTopic("__key*__:mykey"));
        return container;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new KeyChangedListener());
    }

}
