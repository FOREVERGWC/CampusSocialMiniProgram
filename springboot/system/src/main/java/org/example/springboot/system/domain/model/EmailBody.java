package org.example.springboot.system.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 注册邮箱
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "注册邮箱实体", description = "注册邮箱")
public class EmailBody implements Serializable {
    @Serial
    private static final long serialVersionUID = -6310275831300904208L;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @NotBlank(message = "{email.NotBlank}")
    @Email(message = "{email.Invalid}")
    private String email;
}
