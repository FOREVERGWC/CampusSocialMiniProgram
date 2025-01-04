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
import org.example.springboot.system.common.converter.BooleanConverter;
import org.example.springboot.system.common.converter.EnableStatusConverter;
import org.example.springboot.system.common.converter.MenuTypeConverter;
import org.example.springboot.system.common.enums.EnableStatus;
import org.example.springboot.system.common.enums.MenuType;

import java.io.Serial;

/**
 * <p>
 * 菜单
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Schema(name = "菜单实体", description = "菜单")
public class Menu extends BaseEntity {
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
     * 标题
     */
    @Schema(description = "标题")
    @ExcelProperty(value = "标题")
    private String title;
    /**
     * 图标
     */
    @Schema(description = "图标")
    @ExcelProperty(value = "图标")
    private String icon;
    /**
     * 父级菜单ID
     */
    @Schema(description = "父级菜单ID")
    @ExcelProperty(value = "父级菜单ID")
    private Long parentId;
    /**
     * 祖级菜单ID
     */
    @Schema(description = "祖级菜单ID")
    @ExcelProperty(value = "祖级菜单ID")
    private Long ancestorId;
    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    @ExcelProperty(value = "路由地址")
    private String path;
    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    @ExcelProperty(value = "重定向地址")
    private String redirect;
    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    @ExcelProperty(value = "组件路径")
    private String component;
    /**
     * 类型(1目录、2菜单、3按钮)
     */
    @Schema(description = "类型")
    @ExcelProperty(value = "类型", converter = MenuTypeConverter.class)
    @Dict(enumClass = MenuType.class)
    private String type;
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
    /**
     * 可见(0否、1是)
     */
    @Schema(description = "可见")
    @ExcelProperty(value = "可见", converter = BooleanConverter.class)
    private Boolean visible;
}
