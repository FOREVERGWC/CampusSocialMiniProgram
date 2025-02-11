package org.example.springboot.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 用户信息
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "用户信息实体", description = "用户信息")
public class UserEditDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @NotNull(message = "{userId.NotNull}")
    private Long id;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Length(min = 1, max = 20, message = "{nickname.Length}")
    private String nickname;
    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Length(min = 1, max = 20, message = "{name.Length}")
    private String name;
    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;
    /**
     * 性别(0女、1男、2未知)
     */
    @Schema(description = "性别")
    private String gender;
    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDate birthday;
    /**
     * 手机
     */
    @Schema(description = "手机")
    @Pattern(regexp = "^1[3,456789][0-9]{9}$", message = "{phone.Invalid}")
    private String phone;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(message = "{email.Invalid}")
    private String email;
}
