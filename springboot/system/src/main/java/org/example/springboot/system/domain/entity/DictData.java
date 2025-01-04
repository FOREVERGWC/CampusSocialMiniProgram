package org.example.springboot.system.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * 字典数据
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
@Schema(name = "字典数据实体", description = "字典数据")
public class DictData extends BaseEntity {
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
     * 标签
     */
    @Schema(description = "标签")
    @ExcelProperty(value = "标签")
    private String label;
    /**
     * 键值
     */
    @Schema(description = "键值")
    @ExcelProperty(value = "键值")
    private String value;
    /**
     * 类型ID
     */
    @Schema(description = "类型ID")
    @ExcelProperty(value = "类型ID")
    private Long typeId;
    /**
     * 排序
     */
    @Schema(description = "排序")
    @ExcelProperty(value = "排序")
    private Integer sort;
    /**
     * 状态(0禁用、1正常)
     */
    @Schema(description = "状态(0禁用、1正常)")
    @ExcelProperty(value = "状态(0禁用、1正常)")
    private String status;
}
