package org.example.springboot.system.service.impl;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.entity.Dislike;
import org.example.springboot.system.service.ICountDislikeService;
import org.example.springboot.system.service.IDislikeService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 点踩服务实现类
 * </p>
 */
@Service
public class DislikeServiceImpl implements IDislikeService {
    @Resource
    private ICountDislikeService countDislikeService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long handleDislike(Dislike dislike) {
        Long userId = UserUtils.getLoginUserId();
        Long bizId = dislike.getBizId();
        Integer bizType = dislike.getBizType();

        String key = "user:" + userId + "dislike:bizType:" + bizType + ":bizId:" + bizId;
        boolean hasDislike = redisTemplate.opsForValue().get(key) != null;

        Long count;

        if (hasDislike) {
            // 点踩量
            count = countDislikeService.countMinus(bizId, bizType);
            // 点踩历史
            threadPoolTaskExecutor.execute(() -> redisTemplate.delete(key));
        } else {
            // 点踩量
            count = countDislikeService.countPlus(bizId, bizType);
            // 点踩历史
            threadPoolTaskExecutor.execute(() -> {
                redisTemplate.opsForValue().set(key, DateUtil.today());
                redisTemplate.expire(key, 30, TimeUnit.DAYS);
            });
        }

        return count;
    }
}
