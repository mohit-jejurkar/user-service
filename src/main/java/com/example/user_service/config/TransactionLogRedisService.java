package com.example.user_service.config;

import com.example.user_service.dto.TransactionLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TransactionLogRedisService {

    @Autowired
    private  RedisTemplate<Object, Object> redisTemplate;

    private static final String REDIS_LOG_KEY = "TRANSACTION_LOGS";

    public void pushLog(TransactionLogDTO log) {
        redisTemplate.opsForList().rightPush(REDIS_LOG_KEY, log);
    }

}
