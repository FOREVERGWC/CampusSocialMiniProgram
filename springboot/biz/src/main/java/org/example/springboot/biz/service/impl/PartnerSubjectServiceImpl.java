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
import org.example.springboot.biz.domain.dto.PartnerSubjectDto;
import org.example.springboot.biz.domain.entity.PartnerSubject;
import org.example.springboot.biz.domain.vo.PartnerSubjectVo;
import org.example.springboot.biz.mapper.PartnerSubjectMapper;
import org.example.springboot.biz.service.IPartnerSubjectService;
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
 * 组局主题服务实现类
 * </p>
 */
@Service
public class PartnerSubjectServiceImpl extends ServiceImpl<PartnerSubjectMapper, PartnerSubject> implements IPartnerSubjectService, IBaseService<PartnerSubject> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(PartnerSubject entity) {
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(PartnerSubject entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<PartnerSubjectVo> getList(PartnerSubjectDto dto) {
        List<PartnerSubject> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            PartnerSubjectVo vo = new PartnerSubjectVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<PartnerSubjectVo> getPage(PartnerSubjectDto dto) {
        Page<PartnerSubject> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            PartnerSubjectVo vo = new PartnerSubjectVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public PartnerSubjectVo getOne(PartnerSubjectDto dto) {
        PartnerSubject one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        PartnerSubjectVo vo = new PartnerSubjectVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(PartnerSubject entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, PartnerSubject.class, threadPoolTaskExecutor);
    }

    @Override
    public List<PartnerSubject> getPageList(PartnerSubject entity, IPage<PartnerSubject> page) {
        IPage<PartnerSubject> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<PartnerSubject> getWrapper(PartnerSubject entity) {
        LambdaQueryChainWrapper<PartnerSubject> wrapper = lambdaQuery()
                .eq(entity.getId() != null, PartnerSubject::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getName()), PartnerSubject::getName, entity.getName())
                .like(StrUtil.isNotBlank(entity.getIcon()), PartnerSubject::getIcon, entity.getIcon())
                .eq(entity.getDeleted() != null, PartnerSubject::getDeleted, entity.getDeleted());
        if (entity instanceof PartnerSubjectDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    PartnerSubject::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
