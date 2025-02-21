package org.example.springboot.biz.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.biz.domain.entity.Activity;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.vo.CountVo;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 活动
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(name = "活动实体", description = "活动")
public class ActivityVo extends Activity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    private List<Attachment> attachmentList;
    /**
     * 数量
     */
    @Schema(description = "数量")
    private CountVo count;
}
