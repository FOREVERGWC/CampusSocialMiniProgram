package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.entity.User;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 评分
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "评分实体", description = "评分")
public class RateVo extends Rate {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    private List<Attachment> attachmentList;
}
