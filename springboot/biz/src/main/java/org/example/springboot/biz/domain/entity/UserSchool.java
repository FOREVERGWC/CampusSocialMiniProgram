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
import java.time.LocalDate;

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
@TableName("biz_user_school")
@Schema(name = "用户学校实体", description = "用户学校")
public class UserSchool extends BaseEntity {
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
     * 学校ID
     */
    @Schema(description = "学校ID")
    private Long schoolId;
    /**
     * 学号
     */
    @Schema(description = "学号")
    private String studentId;
    /**
     * 入学时间
     */
    @Schema(description = "入学时间")
    private LocalDate intakeDate;
}
