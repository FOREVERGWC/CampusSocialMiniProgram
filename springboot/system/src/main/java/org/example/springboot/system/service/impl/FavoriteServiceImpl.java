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
import org.example.springboot.system.domain.dto.FavoriteDto;
import org.example.springboot.system.domain.entity.Favorite;
import org.example.springboot.system.domain.vo.FavoriteVo;
import org.example.springboot.system.mapper.FavoriteMapper;
import org.example.springboot.system.service.ICountFavoriteService;
import org.example.springboot.system.service.IFavoriteService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收藏服务实现类
 * </p>
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService, IBaseService<Favorite> {
    @Resource
    private ICountFavoriteService countFavoriteService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

//    @Override
//    public boolean save(Favorite entity) {
//        Long userId = UserUtils.getLoginUserId();
//
//        entity.setUserId(userId);
//
//        boolean flag = super.save(entity);
//        countFavoriteService.countPlus(entity.getBizId(), entity.getBizType());
//        return flag;
//    }
//
//    @Override
//    public boolean saveOrUpdate(Favorite entity) {
//        if (entity.getId() == null) {
//            return save(entity);
//        }
//        return super.updateById(entity);
//    }

    @Override
    public List<FavoriteVo> getList(FavoriteDto dto) {
        List<Favorite> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
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
    public IPage<FavoriteVo> getMyPage(FavoriteDto dto) {
        Long userId = UserUtils.getLoginUserId();
        dto.setUserId(userId);
        return getPage(dto);
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

    @Override
    public void exportExcel(Favorite entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Favorite.class, threadPoolTaskExecutor);
    }

    @Transactional
    @Override
    public Long handleFavorite(Favorite favorite) {
        Long userId = UserUtils.getLoginUserId();
        Long bizId = favorite.getBizId();
        Integer bizType = favorite.getBizType();

        favorite.setUserId(userId);

        Favorite one = getWrapper(favorite).one();

        Long count;

        if (one != null) {
            // 收藏量
            count = countFavoriteService.countMinus(bizId, bizType);
            // 收藏历史
            removeById(one);
        } else {
            // 收藏量
            count = countFavoriteService.countPlus(bizId, bizType);
            // 收藏历史
            save(favorite);
        }

        return count;
    }

    @Override
    public List<Favorite> getPageList(Favorite entity, IPage<Favorite> page) {
        IPage<Favorite> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Favorite> getWrapper(Favorite entity) {
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
