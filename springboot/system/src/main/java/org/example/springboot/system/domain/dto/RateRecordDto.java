package org.example.springboot.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.domain.entity.RateRecord;

import java.io.Serial;
import java.util.Map;

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
public class RateRecordDto extends RateRecord {
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
}
