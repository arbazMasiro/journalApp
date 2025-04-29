package com.ahusain.journalapp.service;

import com.ahusain.journalapp.apiresponse.WeatherResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T getDataFromRedisLayer(String key,  Class<T> weatherResponse){
        try {
            Object jsonData = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonData.toString(), weatherResponse);
        } catch (Exception e) {
            log.error("Error", e);
            return null;
        }
    }

    public void setDataIntoRedisLayer(String key, WeatherResponse entity, Long ttl){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(entity);
            redisTemplate.opsForValue().set(key, jsonData, ttl, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("Error", e);
        }
    }
}
