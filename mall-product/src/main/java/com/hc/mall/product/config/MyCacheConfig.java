package com.hc.mall.product.config;


import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class MyCacheConfig {
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        CacheProperties.Redis redis = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        if (redis.getTimeToLive()!=null){
            config = config.entryTtl(redis.getTimeToLive());
        }
        if (redis.getKeyPrefix()!=null){
            config = config.prefixKeysWith(redis.getKeyPrefix());
        }
        if (!redis.isCacheNullValues()){
            config = config.disableCachingNullValues();
        }

        if (!redis.isUseKeyPrefix()){
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
