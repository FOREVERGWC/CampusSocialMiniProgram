package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.dto.CountFavoriteDto;
import org.example.springboot.system.domain.entity.CountFavorite;
import org.example.springboot.system.domain.entity.Favorite;
import org.example.springboot.system.domain.vo.CountFavoriteVo;
import org.example.springboot.system.domain.vo.FavoriteCountVo;
import org.example.springboot.system.mapper.CountFavoriteMapper;
import org.example.springboot.system.mapper.FavoriteMapper;
import org.example.springboot.system.service.ICountFavoriteService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 收藏量服务实现类
 * </p>
 */
@Service
public class CountFavoriteServiceImpl extends ServiceImpl<CountFavoriteMapper, CountFavorite> implements ICountFavoriteService, IBaseService<CountFavorite> {
    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<CountFavoriteVo> getList(CountFavoriteDto dto) {
        List<CountFavorite> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            CountFavoriteVo vo = new CountFavoriteVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountFavoriteVo> getPage(CountFavoriteDto dto) {
        Page<CountFavorite> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            CountFavoriteVo vo = new CountFavoriteVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public CountFavoriteVo getOne(CountFavoriteDto dto) {
        CountFavorite one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        CountFavoriteVo vo = new CountFavoriteVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(CountFavorite entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, CountFavorite.class, threadPoolTaskExecutor);
    }

    @Override
    public Long countPlus(Long bizId, Integer bizType) {
        CountFavorite count = Optional.ofNullable(lambdaQuery()
                        .eq(CountFavorite::getBizId, bizId)
                        .eq(CountFavorite::getBizType, bizType)
                        .one())
                .orElse(CountFavorite.builder()
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
        CountFavorite count = Optional.ofNullable(lambdaQuery()
                        .eq(CountFavorite::getBizId, bizId)
                        .eq(CountFavorite::getBizType, bizType)
                        .one())
                .orElse(CountFavorite.builder()
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
        CountFavorite one = lambdaQuery()
                .select(CountFavorite::getCount)
                .eq(CountFavorite::getBizId, bizId)
                .eq(CountFavorite::getBizType, bizType)
                .one();

        if (one == null) {
            return 0L;
        }

        return one.getCount();
    }

    @Override
    public FavoriteCountVo getCountVoByBizIdAndBizType(Long bizId, Integer bizType) {
        Long userId = UserUtils.getLoginUserId();

        CountFavorite one = lambdaQuery()
                .select(CountFavorite::getCount)
                .eq(CountFavorite::getBizId, bizId)
                .eq(CountFavorite::getBizType, bizType)
                .one();

        if (one == null) {
            return FavoriteCountVo.builder().hasDone(false).num(0L).build();
        }

        boolean flag = favoriteMapper.exists(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getBizId, bizId)
                .eq(Favorite::getBizType, bizType)
                .eq(Favorite::getUserId, userId));

        return FavoriteCountVo.builder().hasDone(flag).num(one.getCount()).build();

    }

    @Override
    public Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType) {
        List<CountFavorite> countList = lambdaQuery()
                .select(CountFavorite::getCount)
                .in(CountFavorite::getBizId, bizIds)
                .eq(CountFavorite::getBizType, bizType)
                .list();

        if (CollectionUtil.isEmpty(countList)) {
            return Map.of();
        }

        return countList.stream().collect(Collectors.toMap(CountFavorite::getBizId, CountFavorite::getCount));
    }

    @Override
    public Map<Long, FavoriteCountVo> mapCountVoByBizIdsAndBizType(List<Long> bizIds, Integer bizType) {
        Long userId = UserUtils.getLoginUserId();

        List<CountFavorite> countList = lambdaQuery()
                .in(CountFavorite::getBizId, bizIds)
                .eq(CountFavorite::getBizType, bizType)
                .list();

        if (CollectionUtil.isEmpty(countList)) {
            return Map.of();
        }

        List<Favorite> favoriteList = favoriteMapper.selectList(new LambdaQueryWrapper<Favorite>()
                .in(Favorite::getBizId, bizIds)
                .eq(Favorite::getBizType, bizType)
                .eq(Favorite::getUserId, userId));

        Map<Long, Favorite> favoriteMap = favoriteList.stream()
                .collect(Collectors.toMap(Favorite::getBizId, Function.identity()));

        return countList.stream().collect(Collectors.toMap(
                CountFavorite::getBizId,
                count -> FavoriteCountVo.builder()
                        .hasDone(favoriteMap.get(count.getBizId()) != null)
                        .num(count.getCount())
                        .build()));
    }

    @Override
    public List<CountFavorite> getPageList(CountFavorite entity, IPage<CountFavorite> page) {
        IPage<CountFavorite> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<CountFavorite> getWrapper(CountFavorite entity) {
        return lambdaQuery()
                .eq(entity.getId() != null, CountFavorite::getId, entity.getId())
                .eq(entity.getBizId() != null, CountFavorite::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountFavorite::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountFavorite::getCount, entity.getCount());
    }
}
