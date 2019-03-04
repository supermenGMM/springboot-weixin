package com.mm;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderFoodApplication.class, args);
	}

	@Autowired
    StringEncryptor stringEncryptor;

//    @Test
//    public void testJasypt() {
//        String root = stringEncryptor.encrypt("root");
//        System.out.println("密码：["+root+"]");
//    }
}
