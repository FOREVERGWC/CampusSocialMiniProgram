package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Permission;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.RolePermissionLink;

import java.io.Serial;

/**
 * <p>
 * 角色、权限关系
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "角色、权限关系实体", description = "角色、权限关系")
public class RolePermissionLinkVo extends RolePermissionLink {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 角色
     */
    @Schema(description = "角色")
    private Role role;
    /**
     * 权限
     */
    @Schema(description = "权限")
    private Permission permission;
}
