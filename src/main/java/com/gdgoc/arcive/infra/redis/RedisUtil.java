package com.gdgoc.arcive.infra.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveValue(String key, Object value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public void saveValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> Optional<T> getValue(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return Optional.empty();
        }

        if (!type.isInstance(value)) {
            throw new IllegalStateException("Redis value type mismatch.");
        }

        return Optional.of(type.cast(value));
    }

    public <T> Optional<T> getAndDeleteValue(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return Optional.empty();
        }

        redisTemplate.delete(key);

        if (!type.isInstance(value)) {
            throw new IllegalStateException("Redis value type mismatch.");
        }

        return Optional.of(type.cast(value));
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
