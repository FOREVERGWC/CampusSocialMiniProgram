package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CommentDto;
import org.example.springboot.system.domain.entity.Comment;
import org.example.springboot.system.domain.vo.CommentVo;
import org.example.springboot.system.service.ICommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论前端控制器
 * </p>
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "评论", description = "评论")
public class CommentController {
    @Resource
    private ICommentService commentService;

    /**
     * 添加、修改评论
     *
     * @param comment 评论
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改评论", description = "添加、修改评论", method = "POST")
    public Result<Void> save(@RequestBody Comment comment) {
        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    /**
     * 删除评论
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除评论", description = "删除评论", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        commentService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询评论列表
     *
     * @param dto 评论
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询评论列表", description = "查询评论列表", method = "GET")
    public Result<List<CommentVo>> getList(CommentDto dto) {
        List<CommentVo> list = commentService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询评论分页
     *
     * @param dto 评论
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询评论分页", description = "查询评论分页", method = "GET")
    public Result<IPage<CommentVo>> getPage(CommentDto dto) {
        IPage<CommentVo> page = commentService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询评论
     *
     * @param dto 评论
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询评论", description = "查询评论", method = "GET")
    public Result<CommentVo> getOne(CommentDto dto) {
        CommentVo vo = commentService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询评论
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询评论", description = "查询评论", method = "GET")
    public Result<CommentVo> getById(@PathVariable Long id) {
        CommentVo vo = commentService.getOne(CommentDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出评论
     *
     * @param entity   评论
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出评论", description = "导出评论", method = "GET")
    public void exportExcel(Comment entity, HttpServletResponse response) {
        commentService.exportExcel(entity, response);
    }
}
