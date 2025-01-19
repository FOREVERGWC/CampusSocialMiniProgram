package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.UserAuthDto;
import org.example.springboot.system.domain.entity.UserAuth;
import org.example.springboot.system.domain.vo.UserAuthVo;
import org.example.springboot.system.service.IUserAuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户三方授权前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user/auth")
@Tag(name = "用户三方授权", description = "用户三方授权")
public class UserAuthController {
    @Resource
    private IUserAuthService userAuthService;

    /**
     * 添加、修改用户三方授权
     *
     * @param userAuth 用户三方授权
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改用户三方授权", description = "添加、修改用户三方授权", method = "POST")
    public Result<Void> save(@RequestBody UserAuth userAuth) {
        userAuthService.saveOrUpdate(userAuth);
        return Result.success();
    }

    /**
     * 删除用户三方授权
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户三方授权", description = "删除用户三方授权", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        userAuthService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询用户三方授权列表
     *
     * @param dto 用户三方授权
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户三方授权列表", description = "查询用户三方授权列表", method = "GET")
    public Result<List<UserAuthVo>> getList(UserAuthDto dto) {
        List<UserAuthVo> list = userAuthService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询用户三方授权分页
     *
     * @param dto 用户三方授权
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询用户三方授权分页", description = "查询用户三方授权分页", method = "GET")
    public Result<IPage<UserAuthVo>> getPage(UserAuthDto dto) {
        IPage<UserAuthVo> page = userAuthService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询用户三方授权
     *
     * @param dto 用户三方授权
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询用户三方授权", description = "查询用户三方授权", method = "GET")
    public Result<UserAuthVo> getOne(UserAuthDto dto) {
        UserAuthVo vo = userAuthService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询用户三方授权
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户三方授权", description = "查询用户三方授权", method = "GET")
    public Result<UserAuthVo> getById(@PathVariable Long id) {
        UserAuthVo vo = userAuthService.getOne(UserAuthDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出用户三方授权
     *
     * @param entity   用户三方授权
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出用户三方授权", description = "导出用户三方授权", method = "GET")
    public void exportExcel(UserAuth entity, HttpServletResponse response) {
        userAuthService.exportExcel(entity, response);
    }
}
