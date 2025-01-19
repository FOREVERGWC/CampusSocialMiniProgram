package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.entity.UserAuth;

import java.io.Serial;

/**
 * <p>
 * 用户三方授权
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "用户三方授权实体", description = "用户三方授权")
public class UserAuthVo extends UserAuth {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
}
