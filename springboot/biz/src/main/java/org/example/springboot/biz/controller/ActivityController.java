package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.ActivityDto;
import org.example.springboot.biz.domain.entity.Activity;
import org.example.springboot.biz.domain.vo.ActivityVo;
import org.example.springboot.biz.service.IActivityService;
import org.example.springboot.common.domain.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 活动前端控制器
 * </p>
 */
@RestController
@RequestMapping("/activity")
@Tag(name = "活动", description = "活动")
public class ActivityController {
    @Resource
    private IActivityService activityService;

    /**
     * 添加、修改活动
     *
     * @param activity 活动
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改活动", description = "添加、修改活动", method = "POST")
    public Result<Void> save(@Validated @RequestBody Activity activity) {
        activityService.saveOrUpdate(activity);
        return Result.success();
    }

    /**
     * 删除活动
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除活动", description = "删除活动", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        activityService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询活动列表
     *
     * @param dto 活动
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询活动列表", description = "查询活动列表", method = "GET")
    public Result<List<ActivityVo>> getList(ActivityDto dto) {
        List<ActivityVo> list = activityService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询活动分页
     *
     * @param dto 活动
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询活动分页", description = "查询活动分页", method = "GET")
    public Result<IPage<ActivityVo>> getPage(ActivityDto dto) {
        IPage<ActivityVo> page = activityService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询活动
     *
     * @param dto 活动
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询活动", description = "查询活动", method = "GET")
    public Result<ActivityVo> getOne(ActivityDto dto) {
        ActivityVo vo = activityService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询活动
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询活动", description = "查询活动", method = "GET")
    public Result<ActivityVo> getById(@PathVariable Long id) {
        ActivityVo vo = activityService.getOne(ActivityDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出活动
     *
     * @param entity   活动
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出活动", description = "导出活动", method = "GET")
    public void exportExcel(Activity entity, HttpServletResponse response) {
        activityService.exportExcel(entity, response);
    }
}
