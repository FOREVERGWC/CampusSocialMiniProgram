package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.dto.CountDislikeDto;
import org.example.springboot.system.domain.entity.CountDislike;
import org.example.springboot.system.domain.vo.CountDislikeVo;
import org.example.springboot.system.mapper.CountDislikeMapper;
import org.example.springboot.system.service.ICountDislikeService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 点踩量服务实现类
 * </p>
 */
@Service
public class CountDislikeServiceImpl extends ServiceImpl<CountDislikeMapper, CountDislike> implements ICountDislikeService, IBaseService<CountDislike> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<CountDislikeVo> getList(CountDislikeDto dto) {
        List<CountDislike> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            CountDislikeVo vo = new CountDislikeVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountDislikeVo> getPage(CountDislikeDto dto) {
        Page<CountDislike> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            CountDislikeVo vo = new CountDislikeVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public CountDislikeVo getOne(CountDislikeDto dto) {
        CountDislike one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        CountDislikeVo vo = new CountDislikeVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(CountDislike entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, CountDislike.class, threadPoolTaskExecutor);
    }

    @Override
    public void countPlus(Long bizId, Integer bizType) {
        CountDislike count = Optional.ofNullable(lambdaQuery()
                        .eq(CountDislike::getBizId, bizId)
                        .eq(CountDislike::getBizType, bizType)
                        .one())
                .orElse(CountDislike.builder()
                        .bizId(bizId)
                        .bizType(bizType)
                        .count(1L)
                        .build());

        saveOrUpdate(count);
    }

    @Override
    public Long getCountByBizIdAndBizType(Long bizId, Integer bizType) {
        CountDislike one = lambdaQuery()
                .select(CountDislike::getCount)
                .eq(CountDislike::getBizId, bizId)
                .eq(CountDislike::getBizType, bizType)
                .one();

        if (one == null) {
            return 0L;
        }

        return one.getCount();
    }

    @Override
    public Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType) {
        List<CountDislike> countList = lambdaQuery()
                .select(CountDislike::getCount)
                .in(CountDislike::getBizId, bizIds)
                .eq(CountDislike::getBizType, bizType)
                .list();

        if (CollectionUtil.isEmpty(countList)) {
            return Map.of();
        }

        return countList.stream().collect(Collectors.toMap(CountDislike::getBizId, CountDislike::getCount));
    }

    @Override
    public List<CountDislike> getPageList(CountDislike entity, IPage<CountDislike> page) {
        IPage<CountDislike> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<CountDislike> getWrapper(CountDislike entity) {
        LambdaQueryChainWrapper<CountDislike> wrapper = lambdaQuery()
                .eq(entity.getId() != null, CountDislike::getId, entity.getId())
                .eq(entity.getBizId() != null, CountDislike::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountDislike::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountDislike::getCount, entity.getCount());
        if (entity instanceof CountDislikeDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    CountDislike::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
