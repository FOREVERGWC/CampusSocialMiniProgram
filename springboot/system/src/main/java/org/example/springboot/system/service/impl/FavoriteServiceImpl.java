package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.FavoriteDto;
import org.example.springboot.system.domain.entity.Favorite;
import org.example.springboot.system.domain.vo.FavoriteVo;
import org.example.springboot.system.mapper.FavoriteMapper;
import org.example.springboot.system.service.IFavoriteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收藏服务实现类
 * </p>
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {
    @Override
    public List<FavoriteVo> getList(FavoriteDto dto) {
        List<Favorite> favoriteList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(favoriteList)) {
            return List.of();
        }
        // 组装VO
        return favoriteList.stream().map(item -> {
            FavoriteVo vo = new FavoriteVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<FavoriteVo> getPage(FavoriteDto dto) {
        Page<Favorite> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            FavoriteVo vo = new FavoriteVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public FavoriteVo getOne(FavoriteDto dto) {
        Favorite one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        FavoriteVo vo = new FavoriteVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 收藏
     * @return 结果
     */
    private LambdaQueryChainWrapper<Favorite> getWrapper(Favorite entity) {
        LambdaQueryChainWrapper<Favorite> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Favorite::getId, entity.getId())
                .eq(entity.getBizId() != null, Favorite::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, Favorite::getBizType, entity.getBizType());
        if (entity instanceof FavoriteDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Favorite::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
