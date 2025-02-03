package org.example.springboot.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.LikeDto;
import org.example.springboot.system.domain.entity.Like;
import org.example.springboot.system.service.ICountLikeService;
import org.example.springboot.system.service.ILikeService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public IPage<Like> getMyPage(LikeDto dto) {
        Long bizId = dto.getBizId();
        Integer bizType = dto.getBizType();
        Long userId = dto.getUserId();
        Integer pageNo = dto.getPageNo();
        Integer pageSize = dto.getPageSize();

        IPage<Like> page = new Page<>(pageNo, pageSize);

        // 构建查询条件
        String keyPattern = "user:" + userId + ":like:bizType:" + (bizType != null ? bizType : "*") + ":bizId:" + (bizId != null ? bizId : "*");

        // 获取符合条件的所有 Redis 键
        Set<String> keys = redisTemplate.keys(keyPattern);

        if (keys == null || keys.isEmpty()) {
            // 如果没有符合条件的 Redis 键，返回空的分页结果
            return page;
        }

        // 从 Redis 获取点赞数据
        List<Like> likeList = new ArrayList<>();
        for (String key : keys) {
            // 获取每个键的点赞记录（假设每个键对应一个点赞记录）
            String value = (String) redisTemplate.opsForValue().get(key);

            // 如果 value 不为空，可以构建 Like 对象并加入到列表中
            if (value != null) {
                Like like = new Like();
                like.setUserId(userId);  // 设置 userId（假设可以从 Redis 键中获取）
                like.setBizType(bizType);
                like.setBizId(bizId);
                like.setCreateTime(LocalDateTime.parse(value));  // 假设存储的是时间
                likeList.add(like);
            }
        }

        // 进行分页
        int start = Math.min((pageNo - 1) * pageSize, likeList.size());
        int end = Math.min(pageNo * pageSize, likeList.size());
        List<Like> pagedLikeList = likeList.subList(start, end);

        // 设置分页结果
        page.setRecords(pagedLikeList);
        page.setTotal(likeList.size());

        return page;
    }

    @Override
    public Long handleLike(Like like) {
        Long userId = UserUtils.getLoginUserId();
        Long bizId = like.getBizId();
        Integer bizType = like.getBizType();

        String key = "user:" + userId + ":like:bizType:" + bizType + ":bizId:" + bizId;
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
