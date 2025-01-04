package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.DictData;
import org.example.springboot.system.domain.entity.DictType;

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
@Schema(name = "字典数据实体", description = "字典数据")
public class DictDataVo extends DictData {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    @Schema(description = "类型")
    private DictType type;
}
