package org.example.springboot.system.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * PC端注册请求体
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "PC端注册请求体", description = "PC端注册请求体")
public class RegisterBody implements Serializable {
    @Serial
    private static final long serialVersionUID = -8064099675697144174L;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank(message = "{username.NotBlank}")
    @Size(min = 1, max = 20, message = "{username.Size}")
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "{password.NotBlank}")
    @Size(min = 1, max = 20, message = "{password.Size}")
    private String password;
    /**
     * 确认密码
     */
    @Schema(description = "确认密码")
    @NotBlank(message = "{confirmPwd.NotBlank}")
    @Size(min = 1, max = 20, message = "{password.Size}")
    private String confirmPwd;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @NotBlank(message = "{email.NotBlank}")
    @Email(message = "{email.Invalid}")
    private String email;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "{code.NotBlank}")
    private String code;
}
