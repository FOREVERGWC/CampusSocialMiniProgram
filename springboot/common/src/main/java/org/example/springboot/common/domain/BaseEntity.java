package org.example.springboot.common.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基本实体
 */
@Schema(name = "基本实体", description = "基本实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1152854041982661573L;
    /**
     * 创建者
     */
    @Schema(description = "创建者")
    @ExcelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改者
     */
    @Schema(description = "修改者")
    @ExcelProperty(value = "修改者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @ExcelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty(value = "备注")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String remark;
}
