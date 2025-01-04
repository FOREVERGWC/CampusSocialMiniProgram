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
 * 附件
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_attachment")
@Schema(name = "附件实体", description = "附件")
public class Attachment extends BaseEntity {
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
     * 散列值
     */
    @Schema(description = "散列值")
    @ExcelProperty(value = "散列值")
    private String hashCode;
    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    @ExcelProperty(value = "业务ID")
    private Long bizId;
    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    @ExcelProperty(value = "业务类型")
    private Integer bizType;
    /**
     * 桶名
     */
    @Schema(description = "桶名")
    @ExcelProperty(value = "桶名")
    private String bucketName;
    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    @ExcelProperty(value = "文件路径")
    private String filePath;
    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    @ExcelProperty(value = "文件名称")
    private String fileName;
    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    @ExcelProperty(value = "文件大小")
    private Long fileSize;
    /**
     * 分片数量
     */
    @Schema(description = "分片数量")
    @ExcelProperty(value = "分片数量")
    private Integer chunkTotal;
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
