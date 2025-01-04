package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Menu;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.RoleMenuLink;

import java.io.Serial;

/**
 * <p>
 * 角色、菜单关系
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "角色、菜单关系实体", description = "角色、菜单关系")
public class RoleMenuLinkVo extends RoleMenuLink {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 角色
     */
    @Schema(description = "角色")
    private Role role;
    /**
     * 菜单
     */
    @Schema(description = "菜单")
    private Menu menu;
}
