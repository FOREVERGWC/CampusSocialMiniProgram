package org.example.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 点赞量
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_count_like")
@Schema(name = "点赞量实体", description = "点赞量")
public class CountLike implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    private Long bizId;
    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private Integer bizType;
    /**
     * 赞
     */
    @Schema(description = "赞")
    private Long count;
}
