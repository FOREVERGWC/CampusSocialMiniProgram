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
import org.example.springboot.biz.domain.dto.ActivityCategoryDto;
import org.example.springboot.biz.domain.entity.ActivityCategory;
import org.example.springboot.biz.domain.vo.ActivityCategoryVo;
import org.example.springboot.biz.mapper.ActivityCategoryMapper;
import org.example.springboot.biz.service.IActivityCategoryService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.DeleteEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动类别服务实现类
 * </p>
 */
@Service
public class ActivityCategoryServiceImpl extends ServiceImpl<ActivityCategoryMapper, ActivityCategory> implements IActivityCategoryService, IBaseService<ActivityCategory> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(ActivityCategory entity) {
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(ActivityCategory entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<ActivityCategoryVo> getList(ActivityCategoryDto dto) {
        List<ActivityCategory> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            ActivityCategoryVo vo = new ActivityCategoryVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<ActivityCategoryVo> getPage(ActivityCategoryDto dto) {
        Page<ActivityCategory> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            ActivityCategoryVo vo = new ActivityCategoryVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public ActivityCategoryVo getOne(ActivityCategoryDto dto) {
        ActivityCategory one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        ActivityCategoryVo vo = new ActivityCategoryVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(ActivityCategory entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, ActivityCategory.class, threadPoolTaskExecutor);
    }

    @Override
    public List<ActivityCategory> getPageList(ActivityCategory entity, IPage<ActivityCategory> page) {
        IPage<ActivityCategory> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<ActivityCategory> getWrapper(ActivityCategory entity) {
        LambdaQueryChainWrapper<ActivityCategory> wrapper = lambdaQuery()
                .eq(entity.getId() != null, ActivityCategory::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getName()), ActivityCategory::getName, entity.getName())
                .eq(entity.getDeleted() != null, ActivityCategory::getDeleted, entity.getDeleted());
        if (entity instanceof ActivityCategoryDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    ActivityCategory::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
