package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 路由
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "路由实体", description = "路由")
public class RouteVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;
    /**
     * 父级路由ID
     */
    @Schema(description = "父级路由ID")
    private Long parentId;
    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
    /**
     * 地址
     */
    @Schema(description = "地址")
    private String path;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
    /**
     * 元数据
     */
    @Schema(description = "元数据")
    private MetaVo meta;
    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;
    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirect;
    /**
     * 子路由
     */
    @Schema(description = "子路由")
    List<RouteVo> children;
}
