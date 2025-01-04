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
 * 评论量
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_count_comment")
@Schema(name = "评论量实体", description = "评论量")
public class CountComment extends BaseEntity {
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
    private Byte bizType;
    /**
     * 评论
     */
    @Schema(description = "评论")
    private Long count;
}
