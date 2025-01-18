package org.example.springboot.system.domain.vo;

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
public class CountVo implements Serializable {
    /**
     * 浏览量
     */
    @Schema(description = "浏览量")
    private Long view;
    /**
     * 点赞数量
     */
    @Schema(description = "点赞数量")
    private LikeCountVo like;
    /**
     * 点踩量
     */
    @Schema(description = "点踩量")
    private Long dislike;
    /**
     * 已点踩
     */
    @Schema(description = "已点踩")
    private Boolean hasDislike;
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
    /**
     * 已收藏
     */
    @Schema(description = "已收藏")
    private Boolean hasFavorite;
}
