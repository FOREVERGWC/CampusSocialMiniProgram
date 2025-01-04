package org.example.springboot.system.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
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
import org.example.springboot.system.common.converter.EnableStatusConverter;
import org.example.springboot.system.common.converter.GenderConverter;
import org.example.springboot.system.common.enums.EnableStatus;
import org.example.springboot.system.common.enums.Gender;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Schema(name = "用户信息实体", description = "用户信息")
public class User extends BaseEntity {
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
     * 用户名
     */
    @Schema(description = "用户名")
    @ExcelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @ExcelIgnore
    private String password;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @ExcelProperty(value = "昵称")
    private String nickname;
    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ExcelProperty(value = "角色名称")
    private String name;
    /**
     * 头像
     */
    @Schema(description = "头像")
    @ExcelProperty(value = "头像")
    private String avatar;
    /**
     * 性别(0女、1男、2未知)
     */
    @Schema(description = "性别")
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    @Dict(enumClass = Gender.class)
    private String gender;
    /**
     * 生日
     */
    @Schema(description = "生日")
    @ExcelProperty(value = "生日")
    private LocalDate birthday;
    /**
     * 状态(0禁用、1正常)
     */
    @Schema(description = "状态")
    @ExcelProperty(value = "状态", converter = EnableStatusConverter.class)
    @Dict(enumClass = EnableStatus.class)
    private String status;
    /**
     * 电话
     */
    @Schema(description = "电话")
    @ExcelProperty(value = "电话")
    private String phone;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @ExcelProperty(value = "邮箱")
    private String email;
    /**
     * 微信小程序开放ID
     */
    @Schema(description = "微信小程序开放ID")
    @ExcelProperty(value = "微信小程序开放ID")
    private String openId;
    /**
     * 余额
     */
    @Schema(description = "余额")
    @ExcelProperty(value = "余额")
    private BigDecimal balance;
    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @ExcelProperty(value = "最后登录时间")
    private LocalDateTime loginTime;

    public User(Long id, String username, String password, String nickname, String name, String avatar, String gender, LocalDate birthday, String status, String phone, String email, String openId, BigDecimal balance, String loginIp, LocalDateTime loginTime, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime, String remark) {
        super(createBy, createTime, updateBy, updateTime, remark);
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
        this.phone = phone;
        this.email = email;
        this.openId = openId;
        this.balance = balance;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
    }
}
