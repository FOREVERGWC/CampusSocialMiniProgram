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
@TableName("biz_partner_subject")
@Schema(name = "组局主题实体", description = "组局主题")
public class PartnerSubject extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;
    /**
     * 逻辑删除(0正常、1删除)
     */
    @Schema(description = "逻辑删除(0正常、1删除)")
    private Integer deleted;
}
