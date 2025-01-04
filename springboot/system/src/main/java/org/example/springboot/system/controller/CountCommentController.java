package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CountCommentDto;
import org.example.springboot.system.domain.entity.CountComment;
import org.example.springboot.system.domain.vo.CountCommentVo;
import org.example.springboot.system.service.ICountCommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论量前端控制器
 * </p>
 */
@RestController
@RequestMapping("/countComment")
@Tag(name = "评论量", description = "评论量")
public class CountCommentController {
    @Resource
    private ICountCommentService countCommentService;

    /**
     * 添加、修改评论量
     *
     * @param countComment 评论量
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改评论量", description = "添加、修改评论量", method = "POST")
    public Result<Void> save(@RequestBody CountComment countComment) {
        countCommentService.saveOrUpdate(countComment);
        return Result.success();
    }

    /**
     * 删除评论量
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除评论量", description = "删除评论量", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        countCommentService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询评论量列表
     *
     * @param dto 评论量
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询评论量列表", description = "查询评论量列表", method = "GET")
    public Result<List<CountCommentVo>> getList(CountCommentDto dto) {
        List<CountCommentVo> list = countCommentService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询评论量分页
     *
     * @param dto 评论量
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询评论量分页", description = "查询评论量分页", method = "GET")
    public Result<IPage<CountCommentVo>> getPage(CountCommentDto dto) {
        IPage<CountCommentVo> page = countCommentService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询评论量
     *
     * @param dto 评论量
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询评论量", description = "查询评论量", method = "GET")
    public Result<CountCommentVo> getOne(CountCommentDto dto) {
        CountCommentVo vo = countCommentService.getOne(dto);
        return Result.success(vo);
    }
}
