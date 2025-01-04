package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Permission;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 权限
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "权限实体", description = "权限")
public class PermissionVo extends Permission {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private String statusText;
    /**
     * 子权限
     */
    @Schema(description = "子权限")
    List<PermissionVo> children;
}
