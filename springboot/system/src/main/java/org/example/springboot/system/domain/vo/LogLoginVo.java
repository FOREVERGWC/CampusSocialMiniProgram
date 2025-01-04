package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.LogLogin;

import java.io.Serial;

/**
 * <p>
 * 登录日志
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "登录日志实体", description = "登录日志")
public class LogLoginVo extends LogLogin {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private String loginTypeText;
}
