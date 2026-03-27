package com.suyash.springlearning.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> jsonNodeClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if (o == null) return null;
            return mapper.convertValue(o , jsonNodeClass);
        }
        catch(Exception e){
            log.error("Exception" , e );
            return null;
        }
    }

    public void set(String key , Object value , Long ttl){
        try {
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
