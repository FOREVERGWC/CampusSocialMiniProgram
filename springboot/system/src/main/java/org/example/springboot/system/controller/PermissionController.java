package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.PermissionDto;
import org.example.springboot.system.domain.entity.Permission;
import org.example.springboot.system.domain.vo.PermissionVo;
import org.example.springboot.system.service.IPermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限前端控制器
 * </p>
 */
@RestController
@RequestMapping("/permission")
@Tag(name = "权限", description = "权限")
public class PermissionController {
    @Resource
    private IPermissionService permissionService;

    /**
     * 添加、修改权限
     *
     * @param permission 权限
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('system:permission:add', 'system:permission:edit')")
    @PostMapping
    @Operation(summary = "添加、修改权限", description = "添加、修改权限", method = "POST")
    public Result<Void> save(@RequestBody Permission permission) {
        permissionService.saveOrUpdate(permission);
        return Result.success();
    }

    /**
     * 删除权限
     *
     * @param ids ID列表
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除权限", description = "删除权限", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        permissionService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询权限列表
     *
     * @param dto 权限
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:list')")
    @GetMapping("/list")
    @Operation(summary = "查询权限列表", description = "查询权限列表", method = "GET")
    public Result<List<PermissionVo>> getList(PermissionDto dto) {
        List<PermissionVo> list = permissionService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询权限树
     *
     * @param dto 权限
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:list')")
    @GetMapping("/tree")
    @Operation(summary = "查询权限树", description = "查询权限树", method = "GET")
    public Result<List<PermissionVo>> getTree(PermissionDto dto) {
        List<PermissionVo> list = permissionService.getTree(dto);
        return Result.success(list);
    }

    /**
     * 查询角色权限树
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:list')")
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色权限树", description = "查询角色权限树", method = "GET")
    public Result<List<PermissionVo>> getRoleTree(@PathVariable Long roleId) {
        List<PermissionVo> list = permissionService.getRoleTree(roleId);
        return Result.success(list);
    }

    /**
     * 查询权限分页
     *
     * @param dto 权限
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:list')")
    @GetMapping("/page")
    @Operation(summary = "查询权限分页", description = "查询权限分页", method = "GET")
    public Result<IPage<PermissionVo>> getPage(PermissionDto dto) {
        IPage<PermissionVo> page = permissionService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询权限
     *
     * @param id 主键ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:query')")
    @GetMapping("/{id}")
    @Operation(summary = "查询权限", description = "查询权限", method = "GET")
    public Result<Permission> getById(@PathVariable Long id) {
        Permission vo = permissionService.getById(id);
        return Result.success(vo);
    }

    /**
     * 查询权限
     *
     * @param dto 权限
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:query')")
    @GetMapping
    @Operation(summary = "查询权限", description = "查询权限", method = "GET")
    public Result<PermissionVo> getOne(PermissionDto dto) {
        PermissionVo vo = permissionService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 导出权限
     *
     * @param permission 权限
     * @param response   响应对象
     */
    @PreAuthorize("hasAnyAuthority('system:permission:export')")
    @GetMapping("/export")
    @Operation(summary = "导出权限", description = "导出权限", method = "GET")
    public void exportExcel(Permission permission, HttpServletResponse response) {
        permissionService.exportExcel(permission, response);
    }

    /**
     * 恢复或停用权限
     *
     * @param id 权限ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:permission:edit')")
    @PutMapping("/status/{id}")
    @Operation(summary = "恢复或停用权限", description = "恢复或停用权限", method = "PUT")
    public Result<Void> handleStatus(@PathVariable Long id) {
        permissionService.handleStatus(id);
        return Result.success();
    }
}
