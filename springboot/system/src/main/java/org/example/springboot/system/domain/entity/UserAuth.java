package org.example.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.common.domain.BaseEntity;

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
@TableName("sys_user_auth")
@Schema(name = "用户三方授权实体", description = "用户三方授权")
public class UserAuth extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;
    /**
     * 认证方式(0微信)
     */
    @Schema(description = "认证方式(0微信)")
    private Integer authType;
    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识")
    private String openId;
    /**
     * 授权令牌
     */
    @Schema(description = "授权令牌")
    private String accessToken;
}
