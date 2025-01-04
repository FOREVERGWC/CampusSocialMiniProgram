package org.example.springboot.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * <p>
 * 文件
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "登录日志实体", description = "登录日志")
public class FileChunkDto implements Serializable {
    /**
     * 文件
     */
    private MultipartFile file;
    /**
     * 业务ID
     */
    private Long bizId;
    /**
     * 业务类型
     */
    private Integer bizType;
    /**
     * 散列值
     */
    private String hashCode;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 分片大小
     */
    private Long chunkSize;
    /**
     * 分片序号
     */
    private Integer chunkIndex;
    /**
     * 分片数量
     */
    private Integer chunkTotal;
}
