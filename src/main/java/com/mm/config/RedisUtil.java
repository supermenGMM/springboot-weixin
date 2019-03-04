package com.mm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {
    @Autowired
    StringRedisTemplate redisTemplate;
    /**
     * 添加锁
     * @return
     */
    public Boolean lock(String key ,String value) {
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(key, value);

        return isAbsent;
    }

    public boolean unlock(String key) {
        return redisTemplate.delete(key);
    }

    public void add(String key, String val) {
        redisTemplate.opsForValue().set(key, val,60, TimeUnit.SECONDS);
    }

    public Long reduce(String key) {
        Long decrement = redisTemplate.opsForValue().decrement(key);
        System.out.println("redis中key="+key+",value="+decrement+"====");
        return decrement;
    }

    public Long increment(String key) {
        Long decrement = redisTemplate.opsForValue().increment(key);
        System.out.println("redis中key="+key+",value="+decrement+"====");
        return decrement;
    }

    public String getVal(String key) {
        String decrement = redisTemplate.opsForValue().get(key);
        System.out.println("redis中key="+key+",value="+decrement+"====");
        return decrement;
    }

}
