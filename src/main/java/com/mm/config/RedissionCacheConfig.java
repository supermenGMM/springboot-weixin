package com.mm.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedissionCacheConfig {
    //    @Bean()
//    RedissonClient redisson() {
//        Config config = new Config();
//        config.useClusterServers()
//            .addNodeAddress(
//                "redis://12.3.10.102:7031",
//                "redis://12.3.10.102:7032",
//                "redis://12.3.10.102:7033",
//                "redis://12.3.10.102:7034",
//                "redis://12.3.10.102:7035",
//                "redis://12.3.10.102:7036"
//            );
//        return Redisson.create(config);
//    }
//
//    @Bean
//    CacheManager cacheManager(@Autowired RedissonClient redissonClient) {
//        Map<String, CacheConfig> config = new HashMap<>();
//        // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
//        config.put("testMap",new CacheConfig(24*60*1000,12*60*1000));
//        return new RedissonSpringCacheManager(redissonClient, config);
//    }
    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient() throws IOException {

        Config config = Config.fromYAML(new ClassPathResource("redisson_dev.yml").getInputStream());
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient){
        Map<String,CacheConfig> config = new HashMap<>(16);
        // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("testMap",new CacheConfig(24*60*1000,12*60*1000));
        return  new RedissonSpringCacheManager(redissonClient,config);
    }


}
