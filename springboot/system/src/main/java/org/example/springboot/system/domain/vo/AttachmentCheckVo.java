package org.example.springboot.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.example.springboot.system.domain.entity.Attachment;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 附件校验
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "附件校验实体", description = "附件校验")
public class AttachmentCheckVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 文件已上传
     */
    @Schema(description = "文件已上传")
    private Boolean hasUpload;
    /**
     * 附件
     */
    @Schema(description = "附件")
    private Attachment attachment;
    /**
     * 缺失分片序号
     */
    @Schema(description = "缺失分片序号")
    private List<Integer> indexList;
}
