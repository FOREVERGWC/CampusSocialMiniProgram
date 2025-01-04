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
 * 菜单安排
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "菜单安排实体", description = "菜单安排")
public class AssignMenuBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 6208495093444309757L;
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @NotNull(message = "{roleId.NotNull}")
    private Long roleId;
    /**
     * 菜单ID列表
     */
    @Schema(description = "菜单ID列表")
    @NotNull(message = "{menuIdList.NotNull}")
    private List<Long> menuIdList;
}
