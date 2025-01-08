package org.example.springboot.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.CountRateDto;
import org.example.springboot.biz.domain.entity.CountRate;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.vo.CountRateVo;
import org.example.springboot.biz.mapper.CountRateMapper;
import org.example.springboot.biz.service.ICountRateService;
import org.example.springboot.biz.service.IRateService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
public class CountRateServiceImpl extends ServiceImpl<CountRateMapper, CountRate> implements ICountRateService, IBaseService<CountRate> {
    @Resource
    private IRateService rateService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<CountRateVo> getList(CountRateDto dto) {
        List<CountRate> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 评分
        List<Long> rateIdList = list.stream().map(CountRate::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
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

    @Override
    public void exportExcel(CountRate entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, CountRate.class, threadPoolTaskExecutor);
    }

    @Override
    public List<CountRate> getPageList(CountRate entity, IPage<CountRate> page) {
        IPage<CountRate> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<CountRate> getWrapper(CountRate entity) {
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
