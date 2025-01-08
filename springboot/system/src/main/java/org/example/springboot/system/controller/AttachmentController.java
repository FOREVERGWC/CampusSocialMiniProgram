package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.AttachmentDto;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.vo.AttachmentVo;
import org.example.springboot.system.service.IAttachmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 附件前端控制器
 * </p>
 */
@RestController
@RequestMapping("/attachment")
@Tag(name = "附件", description = "附件")
public class AttachmentController {
    @Resource
    private IAttachmentService attachmentService;

    /**
     * 添加、修改附件
     *
     * @param attachment 附件
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改附件", description = "添加、修改附件", method = "POST")
    public Result<Void> save(@RequestBody Attachment attachment) {
        attachmentService.saveOrUpdate(attachment);
        return Result.success();
    }

    /**
     * 删除附件
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除附件", description = "删除附件", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        attachmentService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询附件列表
     *
     * @param dto 附件
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询附件列表", description = "查询附件列表", method = "GET")
    public Result<List<AttachmentVo>> getList(AttachmentDto dto) {
        List<AttachmentVo> list = attachmentService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询附件分页
     *
     * @param dto 附件
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询附件分页", description = "查询附件分页", method = "GET")
    public Result<IPage<AttachmentVo>> getPage(AttachmentDto dto) {
        IPage<AttachmentVo> page = attachmentService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询附件
     *
     * @param dto 附件
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询附件", description = "查询附件", method = "GET")
    public Result<AttachmentVo> getOne(AttachmentDto dto) {
        AttachmentVo vo = attachmentService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询附件
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询附件", description = "查询附件", method = "GET")
    public Result<AttachmentVo> getById(@PathVariable Long id) {
        AttachmentVo vo = attachmentService.getOne(AttachmentDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出附件
     *
     * @param entity   附件
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出附件", description = "导出附件", method = "GET")
    public void exportExcel(Attachment entity, HttpServletResponse response) {
        attachmentService.exportExcel(entity, response);
    }
}
