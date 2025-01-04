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
 * 角色安排
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "角色安排实体", description = "角色安排")
public class AssignRoleBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1920444936966763347L;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotNull(message = "{userId.NotNull}")
    private Long userId;
    /**
     * 角色ID列表
     */
    @Schema(description = "角色ID列表")
    @NotNull(message = "{roleIdList.NotNull}")
    private List<Long> roleIdList;
}
