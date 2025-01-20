package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 收藏数量
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "收藏数量实体", description = "收藏数量")
public class FavoriteCountVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 已收藏
     */
    @Schema(description = "已收藏")
    private Boolean hasDone;
    /**
     * 收藏量
     */
    @Schema(description = "收藏量")
    private Long num;
}
