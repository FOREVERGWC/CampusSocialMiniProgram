package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.dto.AttachmentChunkDto;
import org.example.springboot.system.domain.entity.AttachmentChunk;
import org.example.springboot.system.domain.vo.AttachmentChunkVo;
import org.example.springboot.system.mapper.AttachmentChunkMapper;
import org.example.springboot.system.service.IAttachmentChunkService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 附件服务实现类
 * </p>
 */
@Service
public class AttachmentChunkServiceImpl extends ServiceImpl<AttachmentChunkMapper, AttachmentChunk> implements IAttachmentChunkService, IBaseService<AttachmentChunk> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<AttachmentChunkVo> getList(AttachmentChunkDto dto) {
        List<AttachmentChunk> attachmentChunkList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(attachmentChunkList)) {
            return List.of();
        }
        // 组装VO
        return attachmentChunkList.stream().map(item -> {
            AttachmentChunkVo vo = new AttachmentChunkVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<AttachmentChunkVo> getPage(AttachmentChunkDto dto) {
        Page<AttachmentChunk> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            AttachmentChunkVo vo = new AttachmentChunkVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public AttachmentChunkVo getOne(AttachmentChunkDto dto) {
        AttachmentChunk one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        AttachmentChunkVo vo = new AttachmentChunkVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(AttachmentChunk entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, AttachmentChunk.class, threadPoolTaskExecutor);
    }

    @Override
    public List<AttachmentChunk> listByHashCode(String hashCode) {
        return Optional.ofNullable(lambdaQuery()
                        .eq(AttachmentChunk::getHashCode, hashCode)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<AttachmentChunk> getPageList(AttachmentChunk entity, IPage<AttachmentChunk> page) {
        IPage<AttachmentChunk> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<AttachmentChunk> getWrapper(AttachmentChunk entity) {
        LambdaQueryChainWrapper<AttachmentChunk> wrapper = lambdaQuery()
                .eq(entity.getId() != null, AttachmentChunk::getId, entity.getId())
                .eq(entity.getFileId() != null, AttachmentChunk::getFileId, entity.getFileId())
                .like(StrUtil.isNotBlank(entity.getHashCode()), AttachmentChunk::getHashCode, entity.getHashCode())
                .eq(entity.getChunkIndex() != null, AttachmentChunk::getChunkIndex, entity.getChunkIndex())
                .eq(entity.getChunkSize() != null, AttachmentChunk::getChunkSize, entity.getChunkSize())
                .eq(entity.getStatus() != null, AttachmentChunk::getStatus, entity.getStatus());
        if (entity instanceof AttachmentChunkDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    AttachmentChunk::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
