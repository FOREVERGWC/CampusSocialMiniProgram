package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.AttachmentChunkDto;
import org.example.springboot.system.domain.entity.AttachmentChunk;
import org.example.springboot.system.domain.vo.AttachmentChunkVo;

import java.util.List;

/**
 * <p>
 * 附件分片服务类
 * </p>
 */
public interface IAttachmentChunkService extends IService<AttachmentChunk> {
    /**
     * 查询附件分片列表
     *
     * @param dto 附件分片
     * @return 结果
     */
    List<AttachmentChunkVo> getList(AttachmentChunkDto dto);

    /**
     * 查询附件分片分页
     *
     * @param dto 附件分片
     * @return 结果
     */
    IPage<AttachmentChunkVo> getPage(AttachmentChunkDto dto);

    /**
     * 查询附件分片
     *
     * @param dto 附件分片
     * @return 结果
     */
    AttachmentChunkVo getOne(AttachmentChunkDto dto);

    /**
     * 导出附件分片
     *
     * @param entity   附件分片
     * @param response 响应对象
     */
    void exportExcel(AttachmentChunk entity, HttpServletResponse response);

    /**
     * 根据散列值查询附件分片列表
     *
     * @param hashCode 散列值
     * @return 结果
     */
    List<AttachmentChunk> listByHashCode(String hashCode);
}
