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
 * 活动
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("biz_activity")
@Schema(name = "活动实体", description = "活动")
public class Activity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startDatetime;
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endDatetime;
    /**
     * 地点
     */
    @Schema(description = "地点")
    private String location;
}
