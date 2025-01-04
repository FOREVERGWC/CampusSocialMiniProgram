package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 验证码
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "验证码实体", description = "验证码")
public class CaptchaVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enabled;
    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识")
    private String uuid;
    /**
     * 图像
     */
    @Schema(description = "图像")
    private String img;
}
