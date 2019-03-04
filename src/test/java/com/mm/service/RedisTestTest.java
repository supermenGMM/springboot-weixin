package com.mm.service;

import com.mm.pojo.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTestTest {

    @Autowired
    private RedisTest redisTest;
    @Test
    public void findById() {
        ProductInfo byId1 = redisTest.findById("1");
        System.out.println(byId1);
        redisTest.delProduct("1");


        ProductInfo byId2 = redisTest.findById("1");
        System.out.println(byId2);

        redisTest.updateProduct("1");

        ProductInfo byId4 = redisTest.findById("1");
        System.out.println(byId4);

        ProductInfo byId3 = redisTest.findById("2");
        System.out.println(byId3);
    }
}