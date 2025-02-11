package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.SchoolDto;
import org.example.springboot.biz.domain.entity.School;
import org.example.springboot.biz.domain.vo.SchoolVo;
import org.example.springboot.biz.service.ISchoolService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学校前端控制器
 * </p>
 */
@RestController
@RequestMapping("/school")
@Tag(name = "学校", description = "学校")
public class SchoolController {
    @Resource
    private ISchoolService schoolService;

    /**
     * 添加、修改学校
     *
     * @param school 学校
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改学校", description = "添加、修改学校", method = "POST")
    public Result<Void> save(@RequestBody School school) {
        schoolService.saveOrUpdate(school);
        return Result.success();
    }

    /**
     * 删除学校
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除学校", description = "删除学校", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        schoolService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询学校列表
     *
     * @param dto 学校
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询学校列表", description = "查询学校列表", method = "GET")
    public Result<List<SchoolVo>> getList(SchoolDto dto) {
        List<SchoolVo> list = schoolService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询学校分页
     *
     * @param dto 学校
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询学校分页", description = "查询学校分页", method = "GET")
    public Result<IPage<SchoolVo>> getPage(SchoolDto dto) {
        IPage<SchoolVo> page = schoolService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询学校
     *
     * @param dto 学校
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询学校", description = "查询学校", method = "GET")
    public Result<SchoolVo> getOne(SchoolDto dto) {
        SchoolVo vo = schoolService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询学校
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询学校", description = "查询学校", method = "GET")
    public Result<SchoolVo> getById(@PathVariable Long id) {
        SchoolVo vo = schoolService.getOne(SchoolDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出学校
     *
     * @param entity   学校
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出学校", description = "导出学校", method = "GET")
    public void exportExcel(School entity, HttpServletResponse response) {
        schoolService.exportExcel(entity, response);
    }
}
