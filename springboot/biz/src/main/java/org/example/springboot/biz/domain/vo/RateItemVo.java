package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.entity.RateItem;
import org.example.springboot.system.domain.entity.Attachment;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 评分项
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "评分项实体", description = "评分项")
public class RateItemVo extends RateItem {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 评分
     */
    @Schema(description = "评分")
    private Rate rate;
    /**
     * 分数
     */
    @Schema(description = "分数")
    private Double score;
    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    private List<Attachment> attachmentList;
}
