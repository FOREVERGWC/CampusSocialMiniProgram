package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.RoleDto;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.model.AssignMenuBody;
import org.example.springboot.system.domain.model.AssignPermissionBody;
import org.example.springboot.system.domain.vo.RoleVo;
import org.example.springboot.system.service.IRoleMenuLinkService;
import org.example.springboot.system.service.IRolePermissionLinkService;
import org.example.springboot.system.service.IRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色前端控制器
 * </p>
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色", description = "角色")
public class RoleController {
    @Resource
    private IRoleService roleService;
    @Resource
    private IRoleMenuLinkService roleMenuLinkService;
    @Resource
    private IRolePermissionLinkService rolePermissionLinkService;

    /**
     * 添加、修改角色
     *
     * @param role 角色
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('system:role:add', 'system:role:edit')")
    @PostMapping
    @Operation(summary = "添加、修改角色", description = "添加、修改角色", method = "POST")
    public Result<Void> save(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param ids ID列表
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除角色", description = "删除角色", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        roleService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询角色列表
     *
     * @param dto 角色
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:list')")
    @GetMapping("/list")
    @Operation(summary = "查询角色列表", description = "查询角色列表", method = "GET")
    public Result<List<RoleVo>> getList(RoleDto dto) {
        List<RoleVo> list = roleService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询角色分页
     *
     * @param dto 角色
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:list')")
    @GetMapping("/page")
    @Operation(summary = "查询角色分页", description = "查询角色分页", method = "GET")
    public Result<IPage<RoleVo>> getPage(RoleDto dto) {
        IPage<RoleVo> page = roleService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询角色
     *
     * @param dto 角色
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:query')")
    @GetMapping
    @Operation(summary = "查询角色", description = "查询角色", method = "GET")
    public Result<RoleVo> getOne(RoleDto dto) {
        RoleVo vo = roleService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询角色
     *
     * @param id 主键ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:query')")
    @GetMapping("/{id}")
    @Operation(summary = "查询角色", description = "查询角色", method = "GET")
    public Result<RoleVo> getById(@PathVariable Long id) {
        RoleVo vo = roleService.getOne(RoleDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出角色
     *
     * @param entity   角色
     * @param response 响应对象
     */
    @PreAuthorize("hasAnyAuthority('system:role:export')")
    @GetMapping("/export")
    @Operation(summary = "导出角色", description = "导出角色", method = "GET")
    public void exportExcel(Role entity, HttpServletResponse response) {
        roleService.exportExcel(entity, response);
    }

    /**
     * 恢复或停用角色
     *
     * @param id 角色ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:edit')")
    @PutMapping("/status/{id}")
    @Operation(summary = "恢复或停用角色", description = "恢复或停用角色", method = "PUT")
    public Result<Void> handleStatus(@PathVariable Long id) {
        roleService.handleStatus(id);
        return Result.success();
    }

    /**
     * 角色分配菜单
     *
     * @param body 菜单分配信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:assign:menu')")
    @PostMapping("/menu")
    @Operation(summary = "角色分配菜单", description = "角色分配菜单", method = "POST")
    public Result<Void> handleMenu(@Validated @RequestBody AssignMenuBody body) {
        roleMenuLinkService.saveBatchByRoleIdAndMenuIds(body.getRoleId(), body.getMenuIdList());
        return Result.success();
    }

    /**
     * 角色分配权限
     *
     * @param body 权限分配信息
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:role:assign:permission')")
    @PostMapping("/permission")
    @Operation(summary = "角色分配权限", description = "角色分配权限", method = "POST")
    public Result<Void> handlePermission(@Validated @RequestBody AssignPermissionBody body) {
        rolePermissionLinkService.saveBatchByRoleIdAndPermissionIds(body.getRoleId(), body.getPermissionIdList());
        return Result.success();
    }
}
