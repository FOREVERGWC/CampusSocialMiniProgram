package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.CountCommentDto;
import org.example.springboot.system.domain.entity.CountComment;
import org.example.springboot.system.domain.vo.CountCommentVo;
import org.example.springboot.system.mapper.CountCommentMapper;
import org.example.springboot.system.service.ICountCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论量服务实现类
 * </p>
 */
@Service
public class CountCommentServiceImpl extends ServiceImpl<CountCommentMapper, CountComment> implements ICountCommentService {
    @Override
    public List<CountCommentVo> getList(CountCommentDto dto) {
        List<CountComment> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            CountCommentVo vo = new CountCommentVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountCommentVo> getPage(CountCommentDto dto) {
        Page<CountComment> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            CountCommentVo vo = new CountCommentVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public CountCommentVo getOne(CountCommentDto dto) {
        CountComment one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        CountCommentVo vo = new CountCommentVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void countPlus(Long bizId, Integer bizType) {
        CountComment count = Optional.ofNullable(lambdaQuery()
                        .eq(CountComment::getBizId, bizId)
                        .eq(CountComment::getBizType, bizType)
                        .one())
                .orElse(CountComment.builder()
                        .bizId(bizId)
                        .bizType(bizType)
                        .count(0L)
                        .build());

        count.setCount(count.getCount() + 1);

        saveOrUpdate(count);
    }

    @Override
    public Long getCountByBizIdAndBizType(Long bizId, Integer bizType) {
        CountComment one = lambdaQuery()
                .select(CountComment::getCount)
                .eq(CountComment::getBizId, bizId)
                .eq(CountComment::getBizType, bizType)
                .one();

        if (one == null) {
            return 0L;
        }

        return one.getCount();
    }

    @Override
    public Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType) {
        List<CountComment> countList = lambdaQuery()
                .in(CountComment::getBizId, bizIds)
                .eq(CountComment::getBizType, bizType)
                .list();

        if (CollectionUtil.isEmpty(countList)) {
            return Map.of();
        }

        return countList.stream().collect(Collectors.toMap(CountComment::getBizId, CountComment::getCount));
    }

    /**
     * 组装查询包装器
     *
     * @param entity 评论量
     * @return 结果
     */
    private LambdaQueryChainWrapper<CountComment> getWrapper(CountComment entity) {
        return lambdaQuery()
                .eq(entity.getId() != null, CountComment::getId, entity.getId())
                .eq(entity.getBizId() != null, CountComment::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountComment::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountComment::getCount, entity.getCount());
    }
}
