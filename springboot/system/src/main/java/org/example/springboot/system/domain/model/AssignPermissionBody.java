package org.example.springboot.system.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 权限安排
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "权限安排实体", description = "权限安排")
public class AssignPermissionBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 6208495093444309757L;
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @NotNull(message = "{roleId.NotNull}")
    private Long roleId;
    /**
     * 权限ID列表
     */
    @Schema(description = "权限ID列表")
    @NotNull(message = "{permissionIdList.NotNull}")
    private List<Long> permissionIdList;
}
