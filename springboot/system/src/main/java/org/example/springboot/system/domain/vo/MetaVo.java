package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 路由元数据
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "路由元数据实体", description = "路由元数据")
public class MetaVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;
    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;
    /**
     * 隐藏
     */
    @Schema(description = "隐藏")
    private Boolean hidden;
}
