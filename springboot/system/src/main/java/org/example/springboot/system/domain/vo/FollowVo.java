package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Follow;
import org.example.springboot.system.domain.entity.User;

import java.io.Serial;

/**
 * <p>
 * 关注
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "关注实体", description = "关注")
public class FollowVo extends Follow {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 占位符
     */
    @Schema(description = "占位符")
    private String placeholder;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
    /**
     * 关注用户
     */
    @Schema(description = "关注用户")
    private Follow follow;
}
