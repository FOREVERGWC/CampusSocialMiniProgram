package org.example.springboot.system.domain.model.reset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 密码信息
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "密码信息实体", description = "密码信息")
public class ResetBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 5415182666248540807L;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "{password.NotBlank}")
    private String password;
    /**
     * 确认密码
     */
    @Schema(description = "确认密码")
    @NotBlank(message = "{confirmPwd.NotBlank}")
    private String confirmPwd;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @NotBlank(message = "{email.NotBlank}")
    private String email;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "{code.NotBlank}")
    private String code;
}
