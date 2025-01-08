package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.AttachmentDto;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.vo.AttachmentVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件服务类
 * </p>
 */
public interface IAttachmentService extends IService<Attachment> {
    /**
     * 查询附件列表
     *
     * @param dto 附件
     * @return 结果
     */
    List<AttachmentVo> getList(AttachmentDto dto);

    /**
     * 查询附件分页
     *
     * @param dto 附件
     * @return 结果
     */
    IPage<AttachmentVo> getPage(AttachmentDto dto);

    /**
     * 查询附件
     *
     * @param dto 附件
     * @return 结果
     */
    AttachmentVo getOne(AttachmentDto dto);

    /**
     * 导出附件
     *
     * @param entity   附件
     * @param response 响应对象
     */
    void exportExcel(Attachment entity, HttpServletResponse response);

    /**
     * 根据散列值查询附件
     *
     * @param hashCode 散列值
     * @return 结果
     */
    Attachment getByHashCode(String hashCode);

    /**
     * 根据业务ID和业务类型查询附件列表
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    List<Attachment> listByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询附件列表
     *
     * @param bizIdList 业务ID列表
     * @param bizType   业务类型
     * @return 结果
     */
    List<Attachment> listByBizIdsAndBizType(List<Long> bizIdList, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询附件列表分组
     *
     * @param bizIdList 业务ID列表
     * @param bizType   业务类型
     * @return 结果
     */
    Map<Long, List<Attachment>> groupByBizIdsAndBizType(List<Long> bizIdList, Integer bizType);
}
