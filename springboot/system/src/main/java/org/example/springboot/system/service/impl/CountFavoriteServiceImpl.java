package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.CountFavoriteDto;
import org.example.springboot.system.domain.entity.CountFavorite;
import org.example.springboot.system.domain.vo.CountFavoriteVo;
import org.example.springboot.system.mapper.CountFavoriteMapper;
import org.example.springboot.system.service.ICountFavoriteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收藏量服务实现类
 * </p>
 */
@Service
public class CountFavoriteServiceImpl extends ServiceImpl<CountFavoriteMapper, CountFavorite> implements ICountFavoriteService {
    @Override
    public List<CountFavoriteVo> getList(CountFavoriteDto dto) {
        List<CountFavorite> countFavoriteList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(countFavoriteList)) {
            return List.of();
        }
        // 组装VO
        return countFavoriteList.stream().map(item -> {
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

    /**
     * 组装查询包装器
     *
     * @param entity 收藏量
     * @return 结果
     */
    private LambdaQueryChainWrapper<CountFavorite> getWrapper(CountFavorite entity) {
        LambdaQueryChainWrapper<CountFavorite> wrapper = lambdaQuery()
                .eq(entity.getId() != null, CountFavorite::getId, entity.getId())
                .eq(entity.getBizId() != null, CountFavorite::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountFavorite::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountFavorite::getCount, entity.getCount());
        if (entity instanceof CountFavoriteDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    CountFavorite::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
