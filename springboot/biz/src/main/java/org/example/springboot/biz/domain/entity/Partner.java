package org.example.springboot.biz.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.common.domain.BaseEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 * 组局
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("biz_partner")
@Schema(name = "组局实体", description = "组局")
public class Partner extends BaseEntity {
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
     * 标题
     */
    @Schema(description = "标题")
    private String title;
    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;
    /**
     * 主题ID
     */
    @Schema(description = "主题ID")
    private Long subjectId;
    /**
     * 活动人数
     */
    @Schema(description = "活动人数")
    private Integer num;
    /**
     * 截止时间
     */
    @Schema(description = "截止时间")
    private LocalDateTime endTime;
    /**
     * 状态(0未发布、1已发布)
     */
    @Schema(description = "状态(0未发布、1已发布)")
    private String status;
}
