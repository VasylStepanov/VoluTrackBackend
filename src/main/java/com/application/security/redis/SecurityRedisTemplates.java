package com.application.security.redis;

import com.application.security.token.DisabledToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class SecurityRedisTemplates {
    @Value("${security.access-token.expiration}")
    long accessExpiration;

    final String name = "disabledTokens";

    @Autowired
    RedisTemplate<String, String> redisTemplateForDisabledTokens;

    @Bean
    public BoundSetOperations<String, String> boundSetOperationsForDisabledTokens(){
        BoundSetOperations<String, String> boundSetOperations = redisTemplateForDisabledTokens.boundSetOps(name);
        boundSetOperations.expire(accessExpiration, TimeUnit.MILLISECONDS);
        return boundSetOperations;
    }
}
