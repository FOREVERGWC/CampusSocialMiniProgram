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
import org.example.springboot.system.common.converter.EnableStatusConverter;
import org.example.springboot.system.common.enums.EnableStatus;

import java.io.Serial;

/**
 * <p>
 * 权限
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
@Schema(name = "权限实体", description = "权限")
public class Permission extends BaseEntity {
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
     * 名称
     */
    @Schema(description = "名称")
    @ExcelProperty(value = "名称")
    private String name;
    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    @ExcelProperty(value = "权限标识")
    private String code;
    /**
     * 父级权限ID
     */
    @Schema(description = "父级权限ID")
    @ExcelProperty(value = "父级权限ID")
    private Long parentId;
    /**
     * 祖级权限ID
     */
    @Schema(description = "祖级权限ID")
    @ExcelProperty(value = "祖级权限ID")
    private Long ancestorId;
    /**
     * 排序
     */
    @Schema(description = "排序")
    @ExcelProperty(value = "排序")
    private Integer sort;
    /**
     * 状态(0禁用、1正常)
     */
    @Schema(description = "状态")
    @ExcelProperty(value = "状态", converter = EnableStatusConverter.class)
    @Dict(enumClass = EnableStatus.class)
    private String status;
}
