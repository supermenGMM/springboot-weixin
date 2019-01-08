package com.mm.controller;

import com.mm.config.RedisContant;
import com.mm.pojo.SellerInfo;
import com.mm.service.SellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping(value = "/log")
public class SellerUserController {
    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/in")
    public String login(@RequestParam(value = "openid")  String openid, Map<String,Object> map) {
        map.put("hellp","你好啊");
        //1.获取openid，和数据库做匹配。
        SellerInfo byOpenid = sellerInfoService.findByOpenid(openid);
        log.info("sellInfo:"+byOpenid);
        //2.设置token至redis
        //生产token
        UUID token = UUID.randomUUID();
        redisTemplate.opsForValue().set(openid,
            String.format(RedisContant.TOKEN_PREFIX, token),
            RedisContant.EXPIRE, TimeUnit.SECONDS);

        //3.设置token至cookie
        return "/index";
    }

    public void logOff() {

    }
}
