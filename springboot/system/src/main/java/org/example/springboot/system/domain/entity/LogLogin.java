package org.example.springboot.system.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.common.common.annotation.Dict;
import org.example.springboot.common.domain.BaseEntity;
import org.example.springboot.system.common.converter.BooleanStatusConverter;
import org.example.springboot.system.common.converter.LoginTypeConverter;
import org.example.springboot.system.common.enums.LoginType;

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
@TableName("sys_log_login")
@Schema(name = "登录日志实体", description = "登录日志")
public class LogLogin extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @ExcelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录类型(1账密、2邮箱验证码、3手机验证码)
     */
    @Schema(description = "登录类型")
//    @ColumnWidth(value = 10)
    @ExcelProperty(value = "登录类型", converter = LoginTypeConverter.class)
    @Dict(enumClass = LoginType.class)
    private String loginType;
    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
//    @ColumnWidth(value = 10)
    @ExcelProperty(value = "操作系统")
    private String os;
    /**
     * 浏览器
     */
    @Schema(description = "浏览器")
//    @ColumnWidth(value = 10)
    @ExcelProperty(value = "浏览器")
    private String browser;
    /**
     * IP
     */
    @Schema(description = "IP")
//    @ColumnWidth(value = 15)
    @ExcelProperty(value = "IP")
    private String ip;
    /**
     * IP属地
     */
    @Schema(description = "IP属地")
//    @ColumnWidth(value = 25)
    @ExcelProperty(value = "IP属地")
    private String location;
    /**
     * 状态(0失败、1成功)
     */
    @Schema(description = "状态")
    @ExcelProperty(value = "状态", converter = BooleanStatusConverter.class)
    private Boolean status;
    /**
     * 消息
     */
    @Schema(description = "消息")
//    @ColumnWidth(value = 25)
    @ExcelProperty(value = "消息")
    private String msg;
}
