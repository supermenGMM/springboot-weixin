package com.mm.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class CacheConfig {
    @Bean(name = "myCacheManager",value = "myCacheManager")
    public CacheManager getCachemanage() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("sampleCache")));
        return cacheManager;
    }

}
