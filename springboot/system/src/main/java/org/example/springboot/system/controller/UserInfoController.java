package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.UserInfoDto;
import org.example.springboot.system.domain.entity.UserInfo;
import org.example.springboot.system.domain.vo.UserInfoVo;
import org.example.springboot.system.service.IUserInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user/info")
@Tag(name = "用户信息", description = "用户信息")
public class UserInfoController {
    @Resource
    private IUserInfoService userInfoService;

    /**
     * 添加、修改用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改用户信息", description = "添加、修改用户信息", method = "POST")
    public Result<UserInfo> save(@RequestBody UserInfo userInfo) {
        userInfoService.saveOrUpdate(userInfo);
        return Result.success(userInfo);
    }

    /**
     * 删除用户信息
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户信息", description = "删除用户信息", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        userInfoService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询用户信息列表
     *
     * @param dto 用户信息
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户信息列表", description = "查询用户信息列表", method = "GET")
    public Result<List<UserInfoVo>> getList(UserInfoDto dto) {
        List<UserInfoVo> list = userInfoService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询用户信息分页
     *
     * @param dto 用户信息
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询用户信息分页", description = "查询用户信息分页", method = "GET")
    public Result<IPage<UserInfoVo>> getPage(UserInfoDto dto) {
        IPage<UserInfoVo> page = userInfoService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询用户信息
     *
     * @param dto 用户信息
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询用户信息", description = "查询用户信息", method = "GET")
    public Result<UserInfoVo> getOne(UserInfoDto dto) {
        UserInfoVo vo = userInfoService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询我的用户信息
     *
     * @return 结果
     */
    @GetMapping("/my")
    @Operation(summary = "查询我的用户信息", description = "查询我的用户信息", method = "GET")
    public Result<UserInfoVo> getMyOne() {
        UserInfoVo vo = userInfoService.getMyOne();
        return Result.success(vo);
    }

    /**
     * 查询用户信息
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户信息", description = "查询用户信息", method = "GET")
    public Result<UserInfoVo> getById(@PathVariable Long id) {
        UserInfoVo vo = userInfoService.getOne(UserInfoDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出用户信息
     *
     * @param entity   用户信息
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出用户信息", description = "导出用户信息", method = "GET")
    public void exportExcel(UserInfo entity, HttpServletResponse response) {
        userInfoService.exportExcel(entity, response);
    }
}
