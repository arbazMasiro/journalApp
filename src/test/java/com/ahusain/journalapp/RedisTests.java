package com.ahusain.journalapp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTests {


    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisTests(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    @Disabled
    void testRedis() {
        redisTemplate.opsForValue().set("email","email@gmail.com");
        Object salary = redisTemplate.opsForValue().get("salary");
        int a = 1;
    }

}
