package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.School;
import org.example.springboot.biz.domain.entity.UserSchool;
import org.example.springboot.system.domain.entity.User;

import java.io.Serial;

/**
 * <p>
 * 用户学校
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "用户学校实体", description = "用户学校")
public class UserSchoolVo extends UserSchool {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
    /**
     * 学校
     */
    @Schema(description = "学校")
    private School school;
}
