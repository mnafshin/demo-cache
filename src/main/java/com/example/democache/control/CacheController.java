package com.example.democache.control;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

@RestController
@RequestMapping("admin/cache")
public class CacheController {
    private CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping()
    public List<cacheInfo> getCacheInfo() {
        return cacheManager.getCacheNames()
                .stream()
                .map(this::getCacheInfo)
                .toList();
    }

    private cacheInfo getCacheInfo(String cacheName) {
        var cache = Objects.requireNonNull(cacheManager.getCache(cacheName)).getNativeCache();
        assert cache instanceof Cache;
        Cache<Object, Object> nativeCache = (Cache<Object, Object>) cache;
        Set<Object> keys = nativeCache.asMap().keySet();
        CacheStats stats = nativeCache.stats();
        return new cacheInfo(
                cacheName, keys.size(), keys, stats.toString());
    }

    private record cacheInfo(
            String name, int size, Set<Object> keys, String stats) {
    }
}
