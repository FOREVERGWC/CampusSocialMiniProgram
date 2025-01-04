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
 * 字典类型
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
@Schema(name = "字典类型实体", description = "字典类型")
public class DictType extends BaseEntity {
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
     * 字典名称
     */
    @Schema(description = "字典名称")
    @ExcelProperty(value = "字典名称")
    private String name;
    /**
     * 字典标识
     */
    @Schema(description = "字典标识")
    @ExcelProperty(value = "字典标识")
    private String code;
    /**
     * 字典状态(0禁用、1正常)
     */
    @Schema(description = "字典状态(0禁用、1正常)")
    @ExcelProperty(value = "字典状态(0禁用、1正常)")
    private String status;
}
