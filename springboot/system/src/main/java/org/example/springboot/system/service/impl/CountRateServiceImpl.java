package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.CountRateDto;
import org.example.springboot.system.domain.entity.CountRate;
import org.example.springboot.system.domain.entity.Rate;
import org.example.springboot.system.domain.vo.CountRateVo;
import org.example.springboot.system.mapper.CountRateMapper;
import org.example.springboot.system.service.ICountRateService;
import org.example.springboot.system.service.IRateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 评分量服务实现类
 * </p>
 */
@Service
public class CountRateServiceImpl extends ServiceImpl<CountRateMapper, CountRate> implements ICountRateService {
    @Resource
    private IRateService rateService;

    @Override
    public List<CountRateVo> getList(CountRateDto dto) {
        List<CountRate> countRateList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(countRateList)) {
            return List.of();
        }
        // 评分
        List<Long> rateIdList = countRateList.stream().map(CountRate::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 组装VO
        return countRateList.stream().map(item -> {
            CountRateVo vo = new CountRateVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountRateVo> getPage(CountRateDto dto) {
        Page<CountRate> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 评分
        List<Long> rateIdList = info.getRecords().stream().map(CountRate::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            CountRateVo vo = new CountRateVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            return vo;
        });
    }

    @Override
    public CountRateVo getOne(CountRateDto dto) {
        CountRate one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 评分
        Rate rate = Optional.ofNullable(rateService.getById(one.getRateId())).orElse(Rate.builder().build());
        // 组装VO
        CountRateVo vo = new CountRateVo();
        BeanUtils.copyProperties(one, vo);
        vo.setRate(rate);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 评分量
     * @return 结果
     */
    private LambdaQueryChainWrapper<CountRate> getWrapper(CountRate entity) {
        LambdaQueryChainWrapper<CountRate> wrapper = lambdaQuery()
                .eq(entity.getId() != null, CountRate::getId, entity.getId())
                .eq(entity.getRateId() != null, CountRate::getRateId, entity.getRateId())
                .eq(entity.getCount() != null, CountRate::getCount, entity.getCount());
        if (entity instanceof CountRateDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    CountRate::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
