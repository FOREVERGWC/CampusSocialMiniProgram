package org.example.springboot.system.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * 附件分片
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_attachment_chunk")
@Schema(name = "附件分片实体", description = "附件分片")
public class AttachmentChunk extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @ExcelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 文件ID
     */
    @Schema(description = "文件ID")
    @ExcelProperty(value = "文件ID")
    private Long fileId;
    /**
     * 散列值
     */
    @Schema(description = "散列值")
    @ExcelProperty(value = "散列值")
    private String hashCode;
    /**
     * 分片序号
     */
    @Schema(description = "分片序号")
    @ExcelProperty(value = "分片序号")
    private Integer chunkIndex;
    /**
     * 分片大小
     */
    @Schema(description = "分片大小")
    @ExcelProperty(value = "分片大小")
    private Long chunkSize;
    /**
     * 上传状态(0未完成、1已完成)
     */
    @Schema(description = "上传状态")
    @ExcelProperty(value = "上传状态")
    private Boolean status;
}
