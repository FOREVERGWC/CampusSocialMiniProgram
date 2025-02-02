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
import org.example.springboot.biz.domain.dto.ActivityDto;
import org.example.springboot.biz.domain.entity.Activity;
import org.example.springboot.biz.domain.vo.ActivityVo;
import org.example.springboot.biz.mapper.ActivityMapper;
import org.example.springboot.biz.service.IActivityService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动服务实现类
 * </p>
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService, IBaseService<Activity> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<ActivityVo> getList(ActivityDto dto) {
        List<Activity> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            ActivityVo vo = new ActivityVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<ActivityVo> getPage(ActivityDto dto) {
        Page<Activity> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            ActivityVo vo = new ActivityVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public ActivityVo getOne(ActivityDto dto) {
        Activity one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        ActivityVo vo = new ActivityVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(Activity entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Activity.class, threadPoolTaskExecutor);
    }

    @Override
    public List<Activity> getPageList(Activity entity, IPage<Activity> page) {
        IPage<Activity> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Activity> getWrapper(Activity entity) {
        LambdaQueryChainWrapper<Activity> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Activity::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getTitle()), Activity::getTitle, entity.getTitle())
                .like(StrUtil.isNotBlank(entity.getContent()), Activity::getContent, entity.getContent())
                .like(StrUtil.isNotBlank(entity.getLocation()), Activity::getLocation, entity.getLocation());
        if (entity instanceof ActivityDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");
            // 活动时间
            Object startActivityDateTime = params == null ? null : params.get("startActivityDateTime");
            Object endActivityDateTime = params == null ? null : params.get("endActivityDateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Activity::getCreateTime,
                    startCreateTime, endCreateTime);
            if (ObjectUtil.isAllNotEmpty(startActivityDateTime, endActivityDateTime)) {
                wrapper.ge(Activity::getStartDatetime, startActivityDateTime)
                        .le(Activity::getEndDatetime, endActivityDateTime);
            }
        }
        return wrapper;
    }
}
