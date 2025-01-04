package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Menu;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 菜单
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "菜单实体", description = "菜单")
public class MenuVo extends Menu {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    @Schema(description = "类型")
    private String typeText;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private String statusText;
    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    List<MenuVo> children;
}
