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
import org.example.springboot.system.domain.dto.AttachmentDto;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.vo.AttachmentVo;
import org.example.springboot.system.mapper.AttachmentMapper;
import org.example.springboot.system.service.IAttachmentService;
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
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService, IBaseService<Attachment> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<AttachmentVo> getList(AttachmentDto dto) {
        List<Attachment> attachmentList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(attachmentList)) {
            return List.of();
        }
        // 组装VO
        return attachmentList.stream().map(item -> {
            AttachmentVo vo = new AttachmentVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<AttachmentVo> getPage(AttachmentDto dto) {
        Page<Attachment> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            AttachmentVo vo = new AttachmentVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public AttachmentVo getOne(AttachmentDto dto) {
        Attachment one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        AttachmentVo vo = new AttachmentVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(Attachment entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Attachment.class, threadPoolTaskExecutor);
    }

    @Override
    public Attachment getByHashCode(String hashCode) {
        return lambdaQuery()
                .eq(Attachment::getHashCode, hashCode)
                .one();
    }

    @Override
    public List<Attachment> listByBizIdAndBizType(Long bizId, Integer bizType) {
        return listByBizIdAndBizType(List.of(bizId), bizType);
    }

    @Override
    public List<Attachment> listByBizIdAndBizType(List<Long> bizIdList, Integer bizType) {
        return Optional.ofNullable(lambdaQuery()
                        .in(Attachment::getBizId, bizIdList)
                        .eq(Attachment::getBizType, bizType)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<Attachment> getPageList(Attachment entity, IPage<Attachment> page) {
        IPage<Attachment> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Attachment> getWrapper(Attachment entity) {
        LambdaQueryChainWrapper<Attachment> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Attachment::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getHashCode()), Attachment::getHashCode, entity.getHashCode())
                .eq(entity.getBizId() != null, Attachment::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, Attachment::getBizType, entity.getBizType())
                .like(StrUtil.isNotBlank(entity.getBucketName()), Attachment::getBucketName, entity.getBucketName())
                .like(StrUtil.isNotBlank(entity.getFilePath()), Attachment::getFilePath, entity.getFilePath())
                .like(StrUtil.isNotBlank(entity.getFileName()), Attachment::getFileName, entity.getFileName())
                .eq(entity.getFileSize() != null, Attachment::getFileSize, entity.getFileSize())
                .eq(entity.getChunkTotal() != null, Attachment::getChunkTotal, entity.getChunkTotal())
                .eq(entity.getChunkSize() != null, Attachment::getChunkSize, entity.getChunkSize())
                .eq(entity.getStatus() != null, Attachment::getStatus, entity.getStatus());
        if (entity instanceof AttachmentDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Attachment::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
