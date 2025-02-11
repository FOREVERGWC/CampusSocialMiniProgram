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
import java.time.LocalDate;

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
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_info")
@Schema(name = "用户信息实体", description = "用户信息")
public class UserInfo extends BaseEntity {
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
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;
    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDate birthday;
    /**
     * 性别(0女、1男、2未知)
     */
    @Schema(description = "性别(0女、1男、2未知)")
    private String gender;
    /**
     * 国家
     */
    @Schema(description = "国家")
    private String country;
    /**
     * 省份
     */
    @Schema(description = "省份")
    private String province;
    /**
     * 城市
     */
    @Schema(description = "城市")
    private String city;
    /**
     * 职业
     */
    @Schema(description = "职业")
    private String career;
    /**
     * 额外字段
     */
    @Schema(description = "额外字段")
    private String extra;
}
