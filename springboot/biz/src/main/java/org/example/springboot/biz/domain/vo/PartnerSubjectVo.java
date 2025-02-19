package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.PartnerSubject;

import java.io.Serial;

/**
 * <p>
 * 组局主题
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "组局主题实体", description = "组局主题")
public class PartnerSubjectVo extends PartnerSubject {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 占位符
     */
    @Schema(description = "占位符")
    private String placeholder;
}
