package com.example.fashioncommuni.redis;

import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class GetSaveFromRedis {
    private final RedisTemplate redisTemplate;

    public GetSaveFromRedis(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveCategoryScoresData(String key, CategoryScores categoryScores) throws JsonProcessingException {
        redisTemplate.opsForValue().set(key, categoryScores);
    }

    public Set<CategoryScores> getCategoryScoresData(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}