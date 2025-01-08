package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.NoteCategoryDto;
import org.example.springboot.biz.domain.entity.NoteCategory;
import org.example.springboot.biz.domain.vo.NoteCategoryVo;
import org.example.springboot.biz.service.INoteCategoryService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 笔记类别前端控制器
 * </p>
 */
@RestController
@RequestMapping("/noteCategory")
@Tag(name = "笔记类别", description = "笔记类别")
public class NoteCategoryController {
    @Resource
    private INoteCategoryService noteCategoryService;

    /**
     * 添加、修改笔记类别
     *
     * @param noteCategory 笔记类别
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改笔记类别", description = "添加、修改笔记类别", method = "POST")
    public Result<Void> save(@RequestBody NoteCategory noteCategory) {
        noteCategoryService.saveOrUpdate(noteCategory);
        return Result.success();
    }

    /**
     * 删除笔记类别
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除笔记类别", description = "删除笔记类别", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        noteCategoryService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询笔记类别列表
     *
     * @param dto 笔记类别
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询笔记类别列表", description = "查询笔记类别列表", method = "GET")
    public Result<List<NoteCategoryVo>> getList(NoteCategoryDto dto) {
        List<NoteCategoryVo> list = noteCategoryService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询笔记类别分页
     *
     * @param dto 笔记类别
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询笔记类别分页", description = "查询笔记类别分页", method = "GET")
    public Result<IPage<NoteCategoryVo>> getPage(NoteCategoryDto dto) {
        IPage<NoteCategoryVo> page = noteCategoryService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询笔记类别
     *
     * @param dto 笔记类别
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询笔记类别", description = "查询笔记类别", method = "GET")
    public Result<NoteCategoryVo> getOne(NoteCategoryDto dto) {
        NoteCategoryVo vo = noteCategoryService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询笔记类别
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询笔记类别", description = "查询笔记类别", method = "GET")
    public Result<NoteCategoryVo> getById(@PathVariable Long id) {
        NoteCategoryVo vo = noteCategoryService.getOne(NoteCategoryDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出笔记类别
     *
     * @param entity   笔记类别
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出笔记类别", description = "导出笔记类别", method = "GET")
    public void exportExcel(NoteCategory entity, HttpServletResponse response) {
        noteCategoryService.exportExcel(entity, response);
    }
}
