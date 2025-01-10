package org.example.springboot.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateRecordDto;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.entity.RateItem;
import org.example.springboot.biz.domain.entity.RateRecord;
import org.example.springboot.biz.domain.vo.RateRecordVo;
import org.example.springboot.biz.mapper.RateItemMapper;
import org.example.springboot.biz.mapper.RateRecordMapper;
import org.example.springboot.biz.service.ICountRateService;
import org.example.springboot.biz.service.IRateRecordService;
import org.example.springboot.biz.service.IRateService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.service.IUserService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 评分记录服务实现类
 * </p>
 */
@Service
public class RateRecordServiceImpl extends ServiceImpl<RateRecordMapper, RateRecord> implements IRateRecordService, IBaseService<RateRecord> {
    @Resource
    private IRateService rateService;
    @Resource
    private RateItemMapper rateItemMapper;
    @Resource
    private IUserService userService;
    @Resource
    private ICountRateService countRateService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(RateRecord entity) {
        RateItem rateItem = rateItemMapper.selectById(entity.getRateItemId());
        Long userId = UserUtils.getLoginUserId();

        entity.setRateId(rateItem.getRateId());
        entity.setUserId(userId);

        boolean flag = super.save(entity);
        countRateService.countPlus(rateItem.getRateId());
        return flag;
    }

    @Override
    public boolean saveOrUpdate(RateRecord entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<RateRecordVo> getList(RateRecordDto dto) {
        List<RateRecord> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 评分
        List<Long> rateIdList = list.stream().map(RateRecord::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 评分项
        List<Long> rateItemIdList = list.stream().map(RateRecord::getRateItemId).toList();
        List<RateItem> rateItemList = rateItemMapper.selectBatchIds(rateItemIdList);
        Map<Long, RateItem> rateItemMap = rateItemList.stream().collect(Collectors.toMap(RateItem::getId, item -> item));
        // 用户
        List<Long> userIdList = list.stream().map(RateRecord::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            RateRecordVo vo = new RateRecordVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            vo.setRateItem(rateItemMap.getOrDefault(item.getRateItemId(), RateItem.builder().build()));
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RateRecordVo> getPage(RateRecordDto dto) {
        Page<RateRecord> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 评分
        List<Long> rateIdList = info.getRecords().stream().map(RateRecord::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 评分项
        List<Long> rateItemIdList = info.getRecords().stream().map(RateRecord::getRateItemId).toList();
        List<RateItem> rateItemList = rateItemMapper.selectBatchIds(rateItemIdList);
        Map<Long, RateItem> rateItemMap = rateItemList.stream().collect(Collectors.toMap(RateItem::getId, item -> item));
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(RateRecord::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            RateRecordVo vo = new RateRecordVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            vo.setRateItem(rateItemMap.getOrDefault(item.getRateItemId(), RateItem.builder().build()));
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public RateRecordVo getOne(RateRecordDto dto) {
        RateRecord one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 评分
        Rate rate = Optional.ofNullable(rateService.getById(one.getRateId())).orElse(Rate.builder().build());
        // 评分项
        RateItem rateItem = Optional.ofNullable(rateItemMapper.selectById(one.getRateItemId())).orElse(RateItem.builder().build());
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 组装VO
        RateRecordVo vo = new RateRecordVo();
        BeanUtils.copyProperties(one, vo);
        vo.setRate(rate);
        vo.setRateItem(rateItem);
        vo.setUser(user);
        return vo;
    }

    @Override
    public void exportExcel(RateRecord entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, RateRecord.class, threadPoolTaskExecutor);
    }

    @Override
    public Map<Long, Double> mapRateItemAvgScoreByRateItemIdList(List<Long> rateItemIds) {
        List<RateRecord> rateRecordList = lambdaQuery()
                .in(RateRecord::getRateItemId, rateItemIds)
                .list();

        if (CollectionUtil.isEmpty(rateRecordList)) {
            return Map.of();
        }

        return rateRecordList.stream()
                .collect(Collectors.groupingBy(
                        RateRecord::getRateItemId,
                        Collectors.averagingDouble(RateRecord::getScore)
                ));
    }

    @Override
    public List<RateRecord> getPageList(RateRecord entity, IPage<RateRecord> page) {
        IPage<RateRecord> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<RateRecord> getWrapper(RateRecord entity) {
        LambdaQueryChainWrapper<RateRecord> wrapper = lambdaQuery()
                .eq(entity.getId() != null, RateRecord::getId, entity.getId())
                .eq(entity.getRateId() != null, RateRecord::getRateId, entity.getRateId())
                .eq(entity.getRateItemId() != null, RateRecord::getRateItemId, entity.getRateItemId())
                .eq(entity.getUserId() != null, RateRecord::getUserId, entity.getUserId())
                .eq(entity.getScore() != null, RateRecord::getScore, entity.getScore());
        if (entity instanceof RateRecordDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    RateRecord::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
