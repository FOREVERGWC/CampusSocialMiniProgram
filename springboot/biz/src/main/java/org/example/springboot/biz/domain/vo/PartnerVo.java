package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.Partner;
import org.example.springboot.biz.domain.entity.PartnerSubject;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.CountVo;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 组局
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "组局实体", description = "组局")
public class PartnerVo extends Partner {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
    /**
     * 主题
     */
    @Schema(description = "主题")
    private PartnerSubject subject;
    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    private List<Attachment> attachmentList;
    /**
     * 数量
     */
    @Schema(description = "数量")
    private CountVo count;
}
