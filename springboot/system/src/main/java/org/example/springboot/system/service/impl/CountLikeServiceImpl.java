package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.dto.CountLikeDto;
import org.example.springboot.system.domain.entity.CountLike;
import org.example.springboot.system.domain.vo.CountLikeVo;
import org.example.springboot.system.domain.vo.LikeCountVo;
import org.example.springboot.system.mapper.CountLikeMapper;
import org.example.springboot.system.service.ICountLikeService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 点赞量服务实现类
 * </p>
 */
@Service
public class CountLikeServiceImpl extends ServiceImpl<CountLikeMapper, CountLike> implements ICountLikeService, IBaseService<CountLike> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<CountLikeVo> getList(CountLikeDto dto) {
        List<CountLike> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            CountLikeVo vo = new CountLikeVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountLikeVo> getPage(CountLikeDto dto) {
        Page<CountLike> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            CountLikeVo vo = new CountLikeVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public CountLikeVo getOne(CountLikeDto dto) {
        CountLike one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        CountLikeVo vo = new CountLikeVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(CountLike entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, CountLike.class, threadPoolTaskExecutor);
    }

    @Override
    public Long countPlus(Long bizId, Integer bizType) {
        CountLike count = Optional.ofNullable(lambdaQuery()
                        .eq(CountLike::getBizId, bizId)
                        .eq(CountLike::getBizType, bizType)
                        .one())
                .orElse(CountLike.builder()
                        .bizId(bizId)
                        .bizType(bizType)
                        .count(0L)
                        .build());

        count.setCount(count.getCount() + 1);

        saveOrUpdate(count);

        return count.getCount();
    }

    @Override
    public Long countMinus(Long bizId, Integer bizType) {
        CountLike count = Optional.ofNullable(lambdaQuery()
                        .eq(CountLike::getBizId, bizId)
                        .eq(CountLike::getBizType, bizType)
                        .one())
                .orElse(CountLike.builder()
                        .bizId(bizId)
                        .bizType(bizType)
                        .count(1L)
                        .build());

        count.setCount(count.getCount() - 1);

        saveOrUpdate(count);

        return count.getCount();
    }

    @Override
    public Long getCountByBizIdAndBizType(Long bizId, Integer bizType) {
        CountLike one = lambdaQuery()
                .select(CountLike::getCount)
                .eq(CountLike::getBizId, bizId)
                .eq(CountLike::getBizType, bizType)
                .one();

        if (one == null) {
            return 0L;
        }

        return one.getCount();
    }

    @Override
    public Boolean getHasLikeByBizIdAndBizType(Long bizId, Integer bizType) {
        Long userId = UserUtils.getLoginUserId();

        String key = "user:" + userId + "like:bizType:" + bizType + ":bizId:" + bizId;
        return redisTemplate.opsForValue().get(key) != null;
    }

    @Override
    public LikeCountVo getCountVoByBizIdAndBizType(Long bizId, Integer bizType) {
        Long userId = UserUtils.getLoginUserId();

        CountLike one = lambdaQuery()
                .select(CountLike::getCount)
                .eq(CountLike::getBizId, bizId)
                .eq(CountLike::getBizType, bizType)
                .one();

        if (one == null) {
            return LikeCountVo.builder().hasDone(false).num(0L).build();
        }


        String key = "user:" + userId + "like:bizType:" + bizType + ":bizId:" + bizId;
        boolean flag = redisTemplate.opsForValue().get(key) != null;

        return LikeCountVo.builder().hasDone(flag).num(one.getCount()).build();
    }

    @Override
    public Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType) {
        List<CountLike> countList = lambdaQuery()
                .in(CountLike::getBizId, bizIds)
                .eq(CountLike::getBizType, bizType)
                .list();

        if (CollectionUtil.isEmpty(countList)) {
            return Map.of();
        }

        return countList.stream().collect(Collectors.toMap(CountLike::getBizId, CountLike::getCount));
    }

    @Override
    public Map<Long, LikeCountVo> mapCountVoByBizIdsAndBizType(List<Long> bizIds, Integer bizType) {
        Long userId = UserUtils.getLoginUserId();

        List<CountLike> countList = lambdaQuery()
                .in(CountLike::getBizId, bizIds)
                .eq(CountLike::getBizType, bizType)
                .list();

        if (CollectionUtil.isEmpty(countList)) {
            return Map.of();
        }

        List<String> keys = bizIds.stream()
                .map(bizId -> "user:" + userId + "like:bizType:" + bizType + ":bizId:" + bizId)
                .toList();

        Map<Long, Object> doneMap = bizIds.stream()
                .map(item -> new AbstractMap.SimpleEntry<>(item, redisTemplate.opsForValue().get("user:" + userId + "like:bizType:" + bizType + ":bizId:" + item)))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return countList.stream().collect(Collectors.toMap(
                CountLike::getBizId,
                count -> LikeCountVo.builder()
                        .hasDone(doneMap.get(count.getBizId()) != null)
                        .num(count.getCount())
                        .build()
        ));
    }

    @Override
    public List<CountLike> getPageList(CountLike entity, IPage<CountLike> page) {
        IPage<CountLike> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<CountLike> getWrapper(CountLike entity) {
        return lambdaQuery()
                .eq(entity.getId() != null, CountLike::getId, entity.getId())
                .eq(entity.getBizId() != null, CountLike::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountLike::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountLike::getCount, entity.getCount());
    }
}
