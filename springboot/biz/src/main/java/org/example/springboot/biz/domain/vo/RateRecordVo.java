package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.entity.RateItem;
import org.example.springboot.biz.domain.entity.RateRecord;
import org.example.springboot.system.domain.entity.User;

import java.io.Serial;

/**
 * <p>
 * 评分记录
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "评分记录实体", description = "评分记录")
public class RateRecordVo extends RateRecord {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 评分
     */
    @Schema(description = "评分")
    private Rate rate;
    /**
     * 评分项
     */
    @Schema(description = "评分项")
    private RateItem rateItem;
    /**
     * 用户
     */
    @Schema(description = "用户")
    private User user;
}
