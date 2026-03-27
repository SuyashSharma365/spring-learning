package com.suyash.springlearning.Tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testOneRedis(){
        redisTemplate.opsForValue().set("email" , "sharmasuyash@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        System.out.println(email.toString());
    }
}
