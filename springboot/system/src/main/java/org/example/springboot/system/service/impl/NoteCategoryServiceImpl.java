package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.NoteCategoryDto;
import org.example.springboot.system.domain.entity.NoteCategory;
import org.example.springboot.system.domain.vo.NoteCategoryVo;
import org.example.springboot.system.mapper.NoteCategoryMapper;
import org.example.springboot.system.service.INoteCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 笔记类别服务实现类
 * </p>
 */
@Service
public class NoteCategoryServiceImpl extends ServiceImpl<NoteCategoryMapper, NoteCategory> implements INoteCategoryService {
    @Override
    public List<NoteCategoryVo> getList(NoteCategoryDto dto) {
        List<NoteCategory> noteCategoryList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(noteCategoryList)) {
            return List.of();
        }
        // 组装VO
        return noteCategoryList.stream().map(item -> {
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

    /**
     * 组装查询包装器
     *
     * @param entity 笔记类别
     * @return 结果
     */
    private LambdaQueryChainWrapper<NoteCategory> getWrapper(NoteCategory entity) {
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
