package org.example.springboot.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 数量
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "数量实体", description = "数量")
public class CountDto implements Serializable {
    /**
     * 浏览量
     */
    @Schema(description = "浏览量")
    private Long view;
    /**
     * 点赞量
     */
    @Schema(description = "点赞量")
    private Long like;
    /**
     * 点踩量
     */
    @Schema(description = "点踩量")
    private Long dislike;
    /**
     * 评论量
     */
    @Schema(description = "评论量")
    private Long comment;
    /**
     * 收藏量
     */
    @Schema(description = "收藏量")
    private Long favorite;
}
