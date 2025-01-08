package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Comment;
import org.example.springboot.system.domain.entity.User;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 评论
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "评论实体", description = "评论")
public class CommentVo extends Comment {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 回复
     */
    @Schema(description = "回复")
    private CommentVo reply;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    List<CommentVo> children;
}
