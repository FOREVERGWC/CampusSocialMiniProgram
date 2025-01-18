package org.example.springboot.system.service.impl;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.entity.Like;
import org.example.springboot.system.service.ICountLikeService;
import org.example.springboot.system.service.ILikeService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 点赞服务实现类
 * </p>
 */
@Service
public class LikeServiceImpl implements ILikeService {
    @Resource
    private ICountLikeService countLikeService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long handleLike(Like like) {
        Long userId = UserUtils.getLoginUserId();
        Long bizId = like.getBizId();
        Integer bizType = like.getBizType();

        String key = "user:" + userId + "like:bizType:" + bizType + ":bizId:" + bizId;
        boolean hasLike = redisTemplate.opsForValue().get(key) != null;

        Long count;

        if (hasLike) {
            // 点赞量
            count = countLikeService.countMinus(bizId, bizType);
            // 点赞历史
            threadPoolTaskExecutor.execute(() -> redisTemplate.delete(key));
        } else {
            // 点赞量
            count = countLikeService.countPlus(bizId, bizType);
            // 点赞历史
            threadPoolTaskExecutor.execute(() -> {
                redisTemplate.opsForValue().set(key, DateUtil.today());
                redisTemplate.expire(key, 30, TimeUnit.DAYS);
            });
        }

        return count;
    }
}
