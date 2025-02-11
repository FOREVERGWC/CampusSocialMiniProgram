package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.UserDto;
import org.example.springboot.system.domain.dto.UserEditDto;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.model.AssignRoleBody;
import org.example.springboot.system.domain.vo.UserVo;
import org.example.springboot.system.service.IUserRoleLinkService;
import org.example.springboot.system.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息", description = "用户信息")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleLinkService userRoleLinkService;

    /**
     * 添加、修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('system:user:add', 'system:user:edit')")
    @PostMapping
    @Operation(summary = "添加、修改用户信息", description = "添加、修改用户信息", method = "POST")
    public Result<Void> save(@RequestBody User user) {
        userService.saveOrUpdate(user);
        return Result.success();
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:edit')")
    @PatchMapping
    @Operation(summary = "修改用户信息", description = "修改用户信息", method = "PATCH")
    public Result<Void> edit(@Validated @RequestBody UserEditDto user) {
        userService.edit(user);
        return Result.success();
    }

    /**
     * 删除用户信息
     *
     * @param ids ID列表
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户信息", description = "删除用户信息", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询用户信息列表
     *
     * @param dto 用户信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping("/list")
    @Operation(summary = "查询用户信息列表", description = "查询用户信息列表", method = "GET")
    public Result<List<UserVo>> getList(UserDto dto) {
        List<UserVo> list = userService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询用户信息分页
     *
     * @param dto 用户信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping("/page")
    @Operation(summary = "查询用户信息分页", description = "查询用户信息分页", method = "GET")
    public Result<IPage<UserVo>> getPage(UserDto dto) {
        IPage<UserVo> page = userService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询用户信息
     *
     * @param dto 用户信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:query')")
    @GetMapping
    @Operation(summary = "查询用户信息", description = "查询用户信息", method = "GET")
    public Result<UserVo> getOne(UserDto dto) {
        UserVo vo = userService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询用户信息
     *
     * @param id 主键ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:query')")
    @GetMapping("/{id}")
    @Operation(summary = "查询用户信息", description = "查询用户信息", method = "GET")
    public Result<UserVo> getById(@PathVariable Long id) {
        UserVo vo = userService.getOne(UserDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出用户
     *
     * @param entity   用户
     * @param response 响应对象
     */
    @PreAuthorize("hasAnyAuthority('system:user:export')")
    @GetMapping("/export")
    @Operation(summary = "导出用户", description = "导出用户", method = "GET")
    public void exportExcel(User entity, HttpServletResponse response) {
        userService.exportExcel(entity, response);
    }

    /**
     * 解禁或禁用用户
     *
     * @param id 用户ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:edit')")
    @PutMapping("/status/{id}")
    @Operation(summary = "解禁或禁用用户", description = "解禁或禁用用户", method = "PUT")
    public Result<Void> handleStatus(@PathVariable Long id) {
        userService.handleStatus(id);
        return Result.success();
    }

    /**
     * 用户分配角色
     *
     * @param body 角色分配信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:edit')")
    @PostMapping("/role")
    @Operation(summary = "用户分配角色", description = "用户分配角色", method = "POST")
    public Result<Void> handleRole(@Validated @RequestBody AssignRoleBody body) {
        userRoleLinkService.saveBatchByUserIdAndRoleIds(body.getUserId(), body.getRoleIdList());
        return Result.success();
    }

    /**
     * 重置密码
     *
     * @param id 用户ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:user:reset')")
    @PutMapping("/password/reset/{id}")
    @Operation(summary = "重置密码", description = "重置密码", method = "PUT")
    public Result<Void> resetPassword(@PathVariable Long id) {
        userService.resetPassword(id);
        return Result.success();
    }
}
