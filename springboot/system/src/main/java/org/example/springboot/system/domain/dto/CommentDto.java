package org.example.springboot.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Comment;

import java.io.Serial;
import java.util.List;
import java.util.Map;

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
public class CommentDto extends Comment {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 页码
     */
    @JsonIgnore
    private Integer pageNo;
    /**
     * 页面大小
     */
    @JsonIgnore
    private Integer pageSize;
    /**
     * 查询参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
    /**
     * ID列表
     */
    @Schema(description = "ID列表")
    private List<Long> idList;
    /**
     * 祖级回复ID列表
     */
    @Schema(description = "祖级回复ID列表")
    private List<Long> ancestorIdList;
    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * 是否升序
     */
    private Boolean isAsc;
}
