package org.example.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.common.domain.BaseEntity;

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
@TableName("sys_role_permission_link")
@Schema(name = "角色、权限关系实体", description = "角色、权限关系")
public class RolePermissionLink extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;
    /**
     * 权限ID
     */
    @Schema(description = "权限ID")
    private Long permissionId;
}
