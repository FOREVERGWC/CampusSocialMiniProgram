package org.example.springboot.system.domain.model.reset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 邮箱信息
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "邮箱信息实体", description = "邮箱信息")
public class ResetEmailBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 389282648943453137L;
    /**
     * 原始邮箱
     */
    @Schema(description = "原始邮箱")
    @NotBlank(message = "{email.NotBlank}")
    @Email(message = "{email.Invalid}")
    private String email;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "{code.NotBlank}")
    private String code;
    /**
     * 新邮箱
     */
    @Schema(description = "新邮箱")
    @NotBlank(message = "{newEmail.NotBlank}")
    @Email(message = "{newEmail.Invalid}")
    private String newEmail;
    /**
     * 确认邮箱
     */
    @Schema(description = "确认邮箱")
    @NotBlank(message = "{confirmEmail.NotBlank}")
    @Email(message = "{confirmEmail.Invalid}")
    private String confirmEmail;
}
