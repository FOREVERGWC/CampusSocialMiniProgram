package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.RolePermissionLinkDto;
import org.example.springboot.system.domain.entity.RolePermissionLink;
import org.example.springboot.system.domain.vo.RolePermissionLinkVo;
import org.example.springboot.system.service.IRolePermissionLinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色、权限关系前端控制器
 * </p>
 */
@RestController
@RequestMapping("/role/permission/link")
@Tag(name = "角色、权限关系", description = "角色、权限关系")
public class RolePermissionLinkController {
    @Resource
    private IRolePermissionLinkService rolePermissionLinkService;

    /**
     * 添加、修改角色、权限关系
     *
     * @param rolePermissionLink 角色、权限关系
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改角色、权限关系", description = "添加、修改角色、权限关系", method = "POST")
    public Result<Void> save(@RequestBody RolePermissionLink rolePermissionLink) {
        rolePermissionLinkService.saveOrUpdate(rolePermissionLink);
        return Result.success();
    }

    /**
     * 删除角色、权限关系
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除角色、权限关系", description = "删除角色、权限关系", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        rolePermissionLinkService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询角色、权限关系列表
     *
     * @param dto 角色、权限关系
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询角色、权限关系列表", description = "查询角色、权限关系列表", method = "GET")
    public Result<List<RolePermissionLinkVo>> getList(RolePermissionLinkDto dto) {
        List<RolePermissionLinkVo> list = rolePermissionLinkService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询角色、权限关系分页
     *
     * @param dto 角色、权限关系
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询角色、权限关系分页", description = "查询角色、权限关系分页", method = "GET")
    public Result<IPage<RolePermissionLinkVo>> getPage(RolePermissionLinkDto dto) {
        IPage<RolePermissionLinkVo> page = rolePermissionLinkService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询角色、权限关系
     *
     * @param dto 角色、权限关系
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询角色、权限关系", description = "查询角色、权限关系", method = "GET")
    public Result<RolePermissionLinkVo> getOne(RolePermissionLinkDto dto) {
        RolePermissionLinkVo vo = rolePermissionLinkService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询角色、权限关系
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询角色、权限关系", description = "查询角色、权限关系", method = "GET")
    public Result<RolePermissionLinkVo> getById(@PathVariable Long id) {
        RolePermissionLinkVo vo = rolePermissionLinkService.getOne(RolePermissionLinkDto.builder().id(id).build());
        return Result.success(vo);
    }
}
