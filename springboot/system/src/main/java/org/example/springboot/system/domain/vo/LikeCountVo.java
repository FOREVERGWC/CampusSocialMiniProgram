package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 点赞数量
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "点赞数量实体", description = "点赞数量")
public class LikeCountVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 已点赞
     */
    @Schema(description = "已点赞")
    private Boolean hasDone;
    /**
     * 点赞量
     */
    @Schema(description = "点赞量")
    private Long num;
}
