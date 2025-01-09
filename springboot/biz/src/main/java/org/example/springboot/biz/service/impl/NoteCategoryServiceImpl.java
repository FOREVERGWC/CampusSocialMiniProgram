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
import org.example.springboot.biz.domain.dto.NoteCategoryDto;
import org.example.springboot.biz.domain.entity.NoteCategory;
import org.example.springboot.biz.domain.vo.NoteCategoryVo;
import org.example.springboot.biz.mapper.NoteCategoryMapper;
import org.example.springboot.biz.service.INoteCategoryService;
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
 * 笔记类别服务实现类
 * </p>
 */
@Service
public class NoteCategoryServiceImpl extends ServiceImpl<NoteCategoryMapper, NoteCategory> implements INoteCategoryService, IBaseService<NoteCategory> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(NoteCategory entity) {
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(NoteCategory entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<NoteCategoryVo> getList(NoteCategoryDto dto) {
        List<NoteCategory> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            NoteCategoryVo vo = new NoteCategoryVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<NoteCategoryVo> getPage(NoteCategoryDto dto) {
        Page<NoteCategory> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            NoteCategoryVo vo = new NoteCategoryVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public NoteCategoryVo getOne(NoteCategoryDto dto) {
        NoteCategory one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        NoteCategoryVo vo = new NoteCategoryVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(NoteCategory entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, NoteCategory.class, threadPoolTaskExecutor);
    }

    @Override
    public List<NoteCategory> getPageList(NoteCategory entity, IPage<NoteCategory> page) {
        IPage<NoteCategory> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<NoteCategory> getWrapper(NoteCategory entity) {
        LambdaQueryChainWrapper<NoteCategory> wrapper = lambdaQuery()
                .eq(entity.getId() != null, NoteCategory::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getName()), NoteCategory::getName, entity.getName())
                .eq(entity.getDeleted() != null, NoteCategory::getDeleted, entity.getDeleted());
        if (entity instanceof NoteCategoryDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    NoteCategory::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
