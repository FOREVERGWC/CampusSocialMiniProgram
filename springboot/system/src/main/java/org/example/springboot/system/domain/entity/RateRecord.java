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

/**
 * <p>
 * 评分记录
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("biz_rate_record")
@Schema(name = "评分记录实体", description = "评分记录")
public class RateRecord extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 评分ID
     */
    @Schema(description = "评分ID")
    private Long rateId;
    /**
     * 评分项ID
     */
    @Schema(description = "评分项ID")
    private Long rateItemId;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;
    /**
     * 分数
     */
    @Schema(description = "分数")
    private Double score;
}
