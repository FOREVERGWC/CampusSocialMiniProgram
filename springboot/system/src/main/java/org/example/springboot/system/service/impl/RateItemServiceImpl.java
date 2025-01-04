package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.RateItemDto;
import org.example.springboot.system.domain.entity.Rate;
import org.example.springboot.system.domain.entity.RateItem;
import org.example.springboot.system.domain.vo.RateItemVo;
import org.example.springboot.system.mapper.RateItemMapper;
import org.example.springboot.system.service.IRateItemService;
import org.example.springboot.system.service.IRateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 评分项服务实现类
 * </p>
 */
@Service
public class RateItemServiceImpl extends ServiceImpl<RateItemMapper, RateItem> implements IRateItemService {
    @Resource
    private IRateService rateService;

    @Override
    public List<RateItemVo> getList(RateItemDto dto) {
        List<RateItem> rateItemList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(rateItemList)) {
            return List.of();
        }
        // 用户
        List<Long> rateIdList = rateItemList.stream().map(RateItem::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 组装VO
        return rateItemList.stream().map(item -> {
            RateItemVo vo = new RateItemVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RateItemVo> getPage(RateItemDto dto) {
        Page<RateItem> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> rateIdList = info.getRecords().stream().map(RateItem::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            RateItemVo vo = new RateItemVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            return vo;
        });
    }

    @Override
    public RateItemVo getOne(RateItemDto dto) {
        RateItem one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        Rate rate = Optional.ofNullable(rateService.getById(one.getRateId())).orElse(Rate.builder().build());
        // 组装VO
        RateItemVo vo = new RateItemVo();
        BeanUtils.copyProperties(one, vo);
        vo.setRate(rate);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 评分项
     * @return 结果
     */
    private LambdaQueryChainWrapper<RateItem> getWrapper(RateItem entity) {
        LambdaQueryChainWrapper<RateItem> wrapper = lambdaQuery()
                .eq(entity.getId() != null, RateItem::getId, entity.getId())
                .eq(entity.getRateId() != null, RateItem::getRateId, entity.getRateId())
                .like(StrUtil.isNotBlank(entity.getTitle()), RateItem::getTitle, entity.getTitle())
                .like(StrUtil.isNotBlank(entity.getContent()), RateItem::getContent, entity.getContent())
                .eq(entity.getDeleted() != null, RateItem::getDeleted, entity.getDeleted());
        if (entity instanceof RateItemDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    RateItem::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
