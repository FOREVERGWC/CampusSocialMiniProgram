package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.CountLikeDto;
import org.example.springboot.system.domain.entity.CountLike;
import org.example.springboot.system.domain.vo.CountLikeVo;
import org.example.springboot.system.mapper.CountLikeMapper;
import org.example.springboot.system.service.ICountLikeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点赞量服务实现类
 * </p>
 */
@Service
public class CountLikeServiceImpl extends ServiceImpl<CountLikeMapper, CountLike> implements ICountLikeService {
    @Override
    public List<CountLikeVo> getList(CountLikeDto dto) {
        List<CountLike> countLikeList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(countLikeList)) {
            return List.of();
        }
        // 组装VO
        return countLikeList.stream().map(item -> {
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

    /**
     * 组装查询包装器
     *
     * @param entity 点赞量
     * @return 结果
     */
    private LambdaQueryChainWrapper<CountLike> getWrapper(CountLike entity) {
        LambdaQueryChainWrapper<CountLike> wrapper = lambdaQuery()
                .eq(entity.getId() != null, CountLike::getId, entity.getId())
                .eq(entity.getBizId() != null, CountLike::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountLike::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountLike::getCount, entity.getCount());
        if (entity instanceof CountLikeDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    CountLike::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
