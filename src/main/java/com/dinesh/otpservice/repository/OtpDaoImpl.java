package com.dinesh.otpservice.repository;

import com.dinesh.otpservice.modal.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class OtpDaoImpl implements OtpDao{
    private final RedisTemplate redisTemplate;
    private final static String HASH_KEY = "USER";
    public OtpDaoImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public String save(User user) {
       try {
           var uuid = user.getClientId() + ":" + user.getId().replace("-", "");
           redisTemplate.opsForHash().put(uuid, user.getId(), user);
           redisTemplate.expire(uuid, user.getExpirationInSeconds(), TimeUnit.SECONDS);
           return user.getId();
       } catch (Exception e) {
           System.out.println(e.getMessage());
           return null;
       }
    }

    @Override
    public User find(String key, String hashKey) {
        return (User) redisTemplate.opsForHash().get(key, hashKey);
    }
}
