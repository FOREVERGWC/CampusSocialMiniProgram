package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.ActivityCategoryDto;
import org.example.springboot.biz.domain.entity.ActivityCategory;
import org.example.springboot.biz.domain.vo.ActivityCategoryVo;
import org.example.springboot.biz.service.IActivityCategoryService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 活动类别前端控制器
 * </p>
 */
@RestController
@RequestMapping("/activity/category")
@Tag(name = "活动类别", description = "活动类别")
public class ActivityCategoryController {
    @Resource
    private IActivityCategoryService activityCategoryService;

    /**
     * 添加、修改活动类别
     *
     * @param activityCategory 活动类别
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改活动类别", description = "添加、修改活动类别", method = "POST")
    public Result<Void> save(@RequestBody ActivityCategory activityCategory) {
        activityCategoryService.saveOrUpdate(activityCategory);
        return Result.success();
    }

    /**
     * 删除活动类别
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除活动类别", description = "删除活动类别", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        activityCategoryService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询活动类别列表
     *
     * @param dto 活动类别
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询活动类别列表", description = "查询活动类别列表", method = "GET")
    public Result<List<ActivityCategoryVo>> getList(ActivityCategoryDto dto) {
        List<ActivityCategoryVo> list = activityCategoryService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询活动类别分页
     *
     * @param dto 活动类别
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询活动类别分页", description = "查询活动类别分页", method = "GET")
    public Result<IPage<ActivityCategoryVo>> getPage(ActivityCategoryDto dto) {
        IPage<ActivityCategoryVo> page = activityCategoryService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询活动类别
     *
     * @param dto 活动类别
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询活动类别", description = "查询活动类别", method = "GET")
    public Result<ActivityCategoryVo> getOne(ActivityCategoryDto dto) {
        ActivityCategoryVo vo = activityCategoryService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询活动类别
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询活动类别", description = "查询活动类别", method = "GET")
    public Result<ActivityCategoryVo> getById(@PathVariable Long id) {
        ActivityCategoryVo vo = activityCategoryService.getOne(ActivityCategoryDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出活动类别
     *
     * @param entity   活动类别
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出活动类别", description = "导出活动类别", method = "GET")
    public void exportExcel(ActivityCategory entity, HttpServletResponse response) {
        activityCategoryService.exportExcel(entity, response);
    }
}
