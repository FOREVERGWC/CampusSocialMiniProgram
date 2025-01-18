package org.example.springboot.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateItemDto;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.entity.RateItem;
import org.example.springboot.biz.domain.vo.RateItemVo;
import org.example.springboot.biz.mapper.RateItemMapper;
import org.example.springboot.biz.service.IRateItemService;
import org.example.springboot.biz.service.IRateRecordService;
import org.example.springboot.biz.service.IRateService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.BizType;
import org.example.springboot.system.common.enums.DeleteEnum;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.service.IAttachmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
public class RateItemServiceImpl extends ServiceImpl<RateItemMapper, RateItem> implements IRateItemService, IBaseService<RateItem> {
    @Resource
    private IRateService rateService;
    @Resource
    private IRateRecordService rateRecordService;
    @Resource
    private IAttachmentService attachmentService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(RateItem entity) {
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(RateItem entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<RateItemVo> getList(RateItemDto dto) {
        List<RateItem> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 评分
        List<Long> rateIdList = list.stream().map(RateItem::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 分数
        List<Long> idList = list.stream().map(RateItem::getId).toList();
        Map<Long, Double> scoreMap = rateRecordService.mapRateItemAvgScoreByRateItemIdList(idList);
        // 评分项附件
        Map<Long, List<Attachment>> attachmentMap = attachmentService.groupByBizIdsAndBizType(idList, BizType.BIZ_RATE_ITEM.getCode());
        // 组装VO
        return list.stream().map(item -> {
            RateItemVo vo = new RateItemVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            vo.setScore(scoreMap.getOrDefault(item.getRateId(), 10D));
            vo.setAttachmentList(attachmentMap.getOrDefault(item.getId(), List.of()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RateItemVo> getPage(RateItemDto dto) {
        Page<RateItem> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 评分
        List<Long> rateIdList = info.getRecords().stream().map(RateItem::getRateId).toList();
        List<Rate> rateList = rateService.listByIds(rateIdList);
        Map<Long, Rate> rateMap = rateList.stream().collect(Collectors.toMap(Rate::getId, item -> item));
        // 分数
        List<Long> idList = info.getRecords().stream().map(RateItem::getId).toList();
        Map<Long, Double> scoreMap = rateRecordService.mapRateItemAvgScoreByRateItemIdList(idList);
        // 评分项附件
        Map<Long, List<Attachment>> attachmentMap = attachmentService.groupByBizIdsAndBizType(idList, BizType.BIZ_RATE_ITEM.getCode());
        // 组装VO
        return info.convert(item -> {
            RateItemVo vo = new RateItemVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRate(rateMap.getOrDefault(item.getRateId(), Rate.builder().build()));
            vo.setScore(scoreMap.getOrDefault(item.getRateId(), 10D));
            vo.setAttachmentList(attachmentMap.getOrDefault(item.getId(), List.of()));
            return vo;
        });
    }

    @Override
    public RateItemVo getOne(RateItemDto dto) {
        RateItem one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 评分
        Rate rate = Optional.ofNullable(rateService.getById(one.getRateId())).orElse(Rate.builder().build());
        // 评分项附件
        List<Attachment> attachmentList = attachmentService.listByBizIdAndBizType(one.getId(), BizType.BIZ_RATE_ITEM.getCode());
        // 组装VO
        RateItemVo vo = new RateItemVo();
        BeanUtils.copyProperties(one, vo);
        vo.setRate(rate);
        vo.setAttachmentList(attachmentList);

        return vo;
    }

    @Override
    public void exportExcel(RateItem entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, RateItem.class, threadPoolTaskExecutor);
    }

    @Override
    public List<RateItem> getPageList(RateItem entity, IPage<RateItem> page) {
        IPage<RateItem> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<RateItem> getWrapper(RateItem entity) {
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
