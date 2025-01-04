package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.Note;
import org.example.springboot.system.domain.entity.NoteCategory;
import org.example.springboot.system.domain.entity.User;

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
@Schema(name = "笔记实体", description = "笔记")
public class NoteVo extends Note {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
    /**
     * 类别
     */
    @Schema(description = "类别")
    private NoteCategory category;
}
