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

/**
 * <p>
 * 笔记
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("biz_note")
@Schema(name = "笔记实体", description = "笔记")
public class Note extends BaseEntity {
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
     * 类别ID
     */
    @Schema(description = "类别ID")
    private Long categoryId;
    /**
     * 置顶(0否、1是)
     */
    @Schema(description = "置顶(0否、1是)")
    private Boolean top;
    /**
     * 可见性(0私有、1公开)
     */
    @Schema(description = "可见性(0私有、1公开)")
    private String visible;
    /**
     * 允许评论(0否、1是)
     */
    @Schema(description = "允许评论(0否、1是)")
    private Boolean commentable;
    /**
     * 状态(0未发布、1已发布)
     */
    @Schema(description = "状态(0未发布、1已发布)")
    private String status;
    /**
     * 逻辑删除(0正常、1删除)
     */
    @Schema(description = "逻辑删除(0正常、1删除)")
    private Boolean deleted;
}
