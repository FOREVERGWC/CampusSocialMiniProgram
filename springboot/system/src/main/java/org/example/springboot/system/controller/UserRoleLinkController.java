package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.UserRoleLinkDto;
import org.example.springboot.system.domain.entity.UserRoleLink;
import org.example.springboot.system.domain.vo.UserRoleLinkVo;
import org.example.springboot.system.service.IUserRoleLinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户、角色关系前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user/role/link")
@Tag(name = "用户、角色关系", description = "用户、角色关系")
public class UserRoleLinkController {
    @Resource
    private IUserRoleLinkService userRoleLinkService;

    /**
     * 添加、修改用户、角色关系
     *
     * @param userRoleLink 用户、角色关系
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改用户、角色关系", description = "添加、修改用户、角色关系", method = "POST")
    public Result<Void> save(@RequestBody UserRoleLink userRoleLink) {
        userRoleLinkService.saveOrUpdate(userRoleLink);
        return Result.success();
    }

    /**
     * 删除用户、角色关系
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户、角色关系", description = "删除用户、角色关系", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        userRoleLinkService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询用户、角色关系列表
     *
     * @param dto 用户、角色关系
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户、角色关系列表", description = "查询用户、角色关系列表", method = "GET")
    public Result<List<UserRoleLinkVo>> getList(UserRoleLinkDto dto) {
        List<UserRoleLinkVo> list = userRoleLinkService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询用户、角色关系分页
     *
     * @param dto 用户、角色关系
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询用户、角色关系分页", description = "查询用户、角色关系分页", method = "GET")
    public Result<IPage<UserRoleLinkVo>> getPage(UserRoleLinkDto dto) {
        IPage<UserRoleLinkVo> page = userRoleLinkService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询用户、角色关系
     *
     * @param dto 用户、角色关系
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询用户、角色关系", description = "查询用户、角色关系", method = "GET")
    public Result<UserRoleLinkVo> getOne(UserRoleLinkDto dto) {
        UserRoleLinkVo vo = userRoleLinkService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询用户、角色关系
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户、角色关系", description = "查询用户、角色关系", method = "GET")
    public Result<UserRoleLinkVo> getById(@PathVariable Long id) {
        UserRoleLinkVo vo = userRoleLinkService.getOne(UserRoleLinkDto.builder().id(id).build());
        return Result.success(vo);
    }
}
