package com.mm.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
@Configuration
public class CacheConfig {
    @Bean(name = "myCacheManager",value = "myCacheManager")
    public CacheManager getCachemanage() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("sampleCache"),
            new ConcurrentMapCache("sampleCache2")));
        return cacheManager;
    }

}
