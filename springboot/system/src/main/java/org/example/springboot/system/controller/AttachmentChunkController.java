package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.AttachmentChunkDto;
import org.example.springboot.system.domain.entity.AttachmentChunk;
import org.example.springboot.system.domain.vo.AttachmentChunkVo;
import org.example.springboot.system.service.IAttachmentChunkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 附件分片前端控制器
 * </p>
 */
@RestController
@RequestMapping("/attachmentChunk")
@Tag(name = "附件分片", description = "附件分片")
public class AttachmentChunkController {
    @Resource
    private IAttachmentChunkService attachmentChunkService;

    /**
     * 添加、修改附件分片
     *
     * @param attachmentChunk 附件分片
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改附件分片", description = "添加、修改附件分片", method = "POST")
    public Result<Void> save(@RequestBody AttachmentChunk attachmentChunk) {
        attachmentChunkService.saveOrUpdate(attachmentChunk);
        return Result.success();
    }

    /**
     * 删除附件分片
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除附件分片", description = "删除附件分片", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        attachmentChunkService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询附件分片列表
     *
     * @param dto 附件分片
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询附件分片列表", description = "查询附件分片列表", method = "GET")
    public Result<List<AttachmentChunkVo>> getList(AttachmentChunkDto dto) {
        List<AttachmentChunkVo> list = attachmentChunkService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询附件分片分页
     *
     * @param dto 附件分片
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询附件分片分页", description = "查询附件分片分页", method = "GET")
    public Result<IPage<AttachmentChunkVo>> getPage(AttachmentChunkDto dto) {
        IPage<AttachmentChunkVo> page = attachmentChunkService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询附件分片
     *
     * @param dto 附件分片
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询附件分片", description = "查询附件分片", method = "GET")
    public Result<AttachmentChunkVo> getOne(AttachmentChunkDto dto) {
        AttachmentChunkVo vo = attachmentChunkService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询附件分片
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询附件分片", description = "查询附件分片", method = "GET")
    public Result<AttachmentChunkVo> getById(@PathVariable Long id) {
        AttachmentChunkVo vo = attachmentChunkService.getOne(AttachmentChunkDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出附件分片
     *
     * @param entity   附件分片
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出附件分片", description = "导出附件分片", method = "GET")
    public void exportExcel(AttachmentChunk entity, HttpServletResponse response) {
        attachmentChunkService.exportExcel(entity, response);
    }
}
