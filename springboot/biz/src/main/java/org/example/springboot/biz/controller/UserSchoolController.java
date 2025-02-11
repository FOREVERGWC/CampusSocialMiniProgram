package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.UserSchoolDto;
import org.example.springboot.biz.domain.entity.UserSchool;
import org.example.springboot.biz.domain.vo.UserSchoolVo;
import org.example.springboot.biz.service.IUserSchoolService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户学校前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user/school")
@Tag(name = "用户学校", description = "用户学校")
public class UserSchoolController {
    @Resource
    private IUserSchoolService userSchoolService;

    /**
     * 添加、修改用户学校
     *
     * @param userSchool 用户学校
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改用户学校", description = "添加、修改用户学校", method = "POST")
    public Result<Void> save(@RequestBody UserSchool userSchool) {
        userSchoolService.saveOrUpdate(userSchool);
        return Result.success();
    }

    /**
     * 删除用户学校
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户学校", description = "删除用户学校", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        userSchoolService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询用户学校列表
     *
     * @param dto 用户学校
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户学校列表", description = "查询用户学校列表", method = "GET")
    public Result<List<UserSchoolVo>> getList(UserSchoolDto dto) {
        List<UserSchoolVo> list = userSchoolService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询用户学校分页
     *
     * @param dto 用户学校
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询用户学校分页", description = "查询用户学校分页", method = "GET")
    public Result<IPage<UserSchoolVo>> getPage(UserSchoolDto dto) {
        IPage<UserSchoolVo> page = userSchoolService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询用户学校
     *
     * @param dto 用户学校
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询用户学校", description = "查询用户学校", method = "GET")
    public Result<UserSchoolVo> getOne(UserSchoolDto dto) {
        UserSchoolVo vo = userSchoolService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询我的学校
     *
     * @return 结果
     */
    @GetMapping("/my")
    @Operation(summary = "查询我的学校", description = "查询我的学校", method = "GET")
    public Result<UserSchoolVo> getMyOne() {
        UserSchoolVo vo = userSchoolService.getMyOne();
        return Result.success(vo);
    }

    /**
     * 查询用户学校
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户学校", description = "查询用户学校", method = "GET")
    public Result<UserSchoolVo> getById(@PathVariable Long id) {
        UserSchoolVo vo = userSchoolService.getOne(UserSchoolDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出用户学校
     *
     * @param entity   用户学校
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出用户学校", description = "导出用户学校", method = "GET")
    public void exportExcel(UserSchool entity, HttpServletResponse response) {
        userSchoolService.exportExcel(entity, response);
    }
}
