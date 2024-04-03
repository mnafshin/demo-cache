package com.example.democache.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.extern.slf4j.Slf4j;

@EnableCaching
@Configuration(proxyBeanMethods = false)
@Slf4j
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache("book",
                buildCache(100, 20000, 30));
        cacheManager.registerCustomCache("bookSearch",
                buildCache(50, 100, 3));
        return cacheManager;
    }

    private Cache<Object, Object> buildCache(
            int initialCapacity, int maximumSize, int durationInSeconds) {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(durationInSeconds, TimeUnit.SECONDS)
                .evictionListener((Object key, Object value,
                                   RemovalCause cause) -> log.debug("Key {} was evicted ({})", key, cause))
                .removalListener((Object key, Object value,
                                  RemovalCause cause) -> log.debug("Key {} was removed ({})", key, cause))
                .scheduler(Scheduler.systemScheduler())
                .build();
    }
}
