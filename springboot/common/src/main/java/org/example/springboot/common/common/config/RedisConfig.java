package org.example.springboot.common.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springboot.common.common.instance.ObjectMapperInstance;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis缓存序列化、反序列化配置类
 */
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class RedisConfig {
    /**
     * Jackson2JsonRedisSerializer配置
     *
     * @return 结果
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    /**
     * Redis缓存配置
     *
     * @param cacheProperties 缓存配置
     * @return Redis缓存配置
     */
    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        config = config.computePrefixWith(name -> name + ":");
        return config;
    }

    /**
     * Redis缓存管理器
     *
     * @param connectionFactory Redis连接工厂
     * @param cacheProperties   缓存配置
     * @return Redis缓存管理器
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory, CacheProperties cacheProperties) {
        return RedisCacheManager.builder()
                .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration(cacheProperties))
                .build();
    }

    /**
     * 配置RedisTemplate
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jackson2JsonRedisSerializer();
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
