package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.RoleMenuLinkDto;
import org.example.springboot.system.domain.entity.RoleMenuLink;
import org.example.springboot.system.domain.vo.RoleMenuLinkVo;
import org.example.springboot.system.service.IRoleMenuLinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色、菜单关系前端控制器
 * </p>
 */
@RestController
@RequestMapping("/role/menu/link")
@Tag(name = "角色、菜单关系", description = "角色、菜单关系")
public class RoleMenuLinkController {
    @Resource
    private IRoleMenuLinkService roleMenuLinkService;

    /**
     * 添加、修改角色、菜单关系
     *
     * @param roleMenuLink 角色、菜单关系
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改角色、菜单关系", description = "添加、修改角色、菜单关系", method = "POST")
    public Result<Void> save(@RequestBody RoleMenuLink roleMenuLink) {
        roleMenuLinkService.saveOrUpdate(roleMenuLink);
        return Result.success();
    }

    /**
     * 删除角色、菜单关系
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除角色、菜单关系", description = "删除角色、菜单关系", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        roleMenuLinkService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询角色、菜单关系列表
     *
     * @param dto 角色、菜单关系
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询角色、菜单关系列表", description = "查询角色、菜单关系列表", method = "GET")
    public Result<List<RoleMenuLinkVo>> getList(RoleMenuLinkDto dto) {
        List<RoleMenuLinkVo> list = roleMenuLinkService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询角色、菜单关系分页
     *
     * @param dto 角色、菜单关系
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询角色、菜单关系分页", description = "查询角色、菜单关系分页", method = "GET")
    public Result<IPage<RoleMenuLinkVo>> getPage(RoleMenuLinkDto dto) {
        IPage<RoleMenuLinkVo> page = roleMenuLinkService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询角色、菜单关系
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询角色、菜单关系", description = "查询角色、菜单关系", method = "GET")
    public Result<RoleMenuLink> getById(@PathVariable Long id) {
        RoleMenuLink vo = roleMenuLinkService.getById(id);
        return Result.success(vo);
    }

    /**
     * 查询角色、菜单关系
     *
     * @param dto 角色、菜单关系
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询角色、菜单关系", description = "查询角色、菜单关系", method = "GET")
    public Result<RoleMenuLinkVo> getOne(RoleMenuLinkDto dto) {
        RoleMenuLinkVo vo = roleMenuLinkService.getOne(dto);
        return Result.success(vo);
    }
}
