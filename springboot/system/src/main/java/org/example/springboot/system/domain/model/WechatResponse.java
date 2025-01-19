package org.example.springboot.system.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 微信登录响应体
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "微信登录响应体", description = "微信登录响应体")
public class WechatResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -1168381873022462495L;
    /**
     * 错误码
     */
    @Schema(description = "错误码")
    private Integer errcode;
    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    private String errmsg;
    /**
     * rid
     */
    @Schema(description = "rid")
    private String rid;
    /**
     * 用户唯一标识码
     */
    @Schema(description = "用户唯一标识码")
    private String openid;
    /**
     * 用户通用唯一标识码
     */
    @Schema(description = "用户通用唯一标识码")
    private String unionid;
    /**
     * 会话密钥
     */
    @Schema(description = "会话密钥")
    private String session_key;
}
