package com.cesske.mps.config;

import com.google.common.base.Joiner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 缓存配置
 */
@Configuration
@EnableCaching
@SuppressWarnings("unchecked")
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return (target, method, params) -> Joiner.on("").join(target.getClass().getName(), method.getName(), params);

    }


    @Bean
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        template.setValueSerializer(fastJsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return new RedisCacheManager(redisTemplate);
    }
}
