package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.MenuDto;
import org.example.springboot.system.domain.entity.Menu;
import org.example.springboot.system.domain.vo.MenuVo;
import org.example.springboot.system.service.IMenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单前端控制器
 * </p>
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "菜单", description = "菜单")
public class MenuController {
    @Resource
    private IMenuService menuService;

    /**
     * 添加、修改菜单
     *
     * @param menu 菜单
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('system:menu:add', 'system:menu:edit')")
    @PostMapping
    @Operation(summary = "添加、修改菜单", description = "添加、修改菜单", method = "POST")
    public Result<Void> save(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    /**
     * 删除菜单
     *
     * @param ids ID列表
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除菜单", description = "删除菜单", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        menuService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询菜单列表
     *
     * @param dto 菜单
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:list')")
    @GetMapping("/list")
    @Operation(summary = "查询菜单列表", description = "查询菜单列表", method = "GET")
    public Result<List<MenuVo>> getList(MenuDto dto) {
        List<MenuVo> list = menuService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询菜单树
     *
     * @param dto 菜单
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:list')")
    @GetMapping("/tree")
    @Operation(summary = "查询菜单树", description = "查询菜单树", method = "GET")
    public Result<List<MenuVo>> getTree(MenuDto dto) {
        List<MenuVo> list = menuService.getTree(dto);
        return Result.success(list);
    }

    /**
     * 查询角色菜单树
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:list')")
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色菜单树", description = "查询角色菜单树", method = "GET")
    public Result<List<MenuVo>> getRoleTree(@PathVariable Long roleId) {
        List<MenuVo> list = menuService.getRoleTree(roleId);
        return Result.success(list);
    }

    /**
     * 查询菜单分页
     *
     * @param dto 菜单
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:list')")
    @GetMapping("/page")
    @Operation(summary = "查询菜单分页", description = "查询菜单分页", method = "GET")
    public Result<IPage<MenuVo>> getPage(MenuDto dto) {
        IPage<MenuVo> page = menuService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询菜单
     *
     * @param dto 菜单
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:query')")
    @GetMapping
    @Operation(summary = "查询菜单", description = "查询菜单", method = "GET")
    public Result<MenuVo> getOne(MenuDto dto) {
        MenuVo vo = menuService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询菜单
     *
     * @param id 主键ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:query')")
    @GetMapping("/{id}")
    @Operation(summary = "查询菜单", description = "查询菜单", method = "GET")
    public Result<MenuVo> getById(@PathVariable Long id) {
        MenuVo vo = menuService.getOne(MenuDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出菜单
     *
     * @param entity   菜单
     * @param response 响应对象
     */
    @PreAuthorize("hasAnyAuthority('system:menu:export')")
    @GetMapping("/export")
    @Operation(summary = "导出菜单", description = "导出菜单", method = "GET")
    public void exportExcel(Menu entity, HttpServletResponse response) {
        menuService.exportExcel(entity, response);
    }

    /**
     * 恢复或停用菜单
     *
     * @param id 菜单ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:edit')")
    @PutMapping("/status/{id}")
    @Operation(summary = "恢复或停用菜单", description = "恢复或停用菜单", method = "PUT")
    public Result<Void> handleStatus(@PathVariable Long id) {
        menuService.handleStatus(id);
        return Result.success();
    }

    /**
     * 显示或隐藏菜单
     *
     * @param id 菜单ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:menu:edit')")
    @PutMapping("/visible/{id}")
    @Operation(summary = "显示或隐藏菜单", description = "显示或隐藏菜单", method = "PUT")
    public Result<Void> handleVisible(@PathVariable Long id) {
        menuService.handleVisible(id);
        return Result.success();
    }
}
