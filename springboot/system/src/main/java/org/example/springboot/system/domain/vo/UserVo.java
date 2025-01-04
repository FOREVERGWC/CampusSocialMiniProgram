package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.User;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 用户信息
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "用户信息实体", description = "用户信息")
public class UserVo extends User {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;
    /**
     * 状态
     */
    @Schema(description = "性别")
    private String genderText;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private String statusText;
    /**
     * 角色ID列表
     */
    @Schema(description = "角色ID列表")
    private List<Long> roleIdList;
    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<Role> roleList;
}
