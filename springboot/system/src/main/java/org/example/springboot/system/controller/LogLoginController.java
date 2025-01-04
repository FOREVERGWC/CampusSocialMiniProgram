package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.LogLoginDto;
import org.example.springboot.system.domain.entity.LogLogin;
import org.example.springboot.system.domain.vo.LogLoginVo;
import org.example.springboot.system.service.ILogLoginService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 登录日志前端控制器
 * </p>
 */
@RestController
@RequestMapping("/log/login")
@Tag(name = "登录日志", description = "登录日志")
public class LogLoginController {
    @Resource
    private ILogLoginService logLoginService;

    /**
     * 添加、修改登录日志
     *
     * @param logLogin 登录日志
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改登录日志", description = "添加、修改登录日志", method = "POST")
    public Result<Void> save(@RequestBody LogLogin logLogin) {
        logLoginService.saveOrUpdate(logLogin);
        return Result.success();
    }

    /**
     * 删除登录日志
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除登录日志", description = "删除登录日志", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        logLoginService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询登录日志列表
     *
     * @param dto 登录日志
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询登录日志列表", description = "查询登录日志列表", method = "GET")
    public Result<List<LogLoginVo>> getList(LogLoginDto dto) {
        List<LogLoginVo> list = logLoginService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询登录日志分页
     *
     * @param dto 登录日志
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询登录日志分页", description = "查询登录日志分页", method = "GET")
    public Result<IPage<LogLoginVo>> getPage(LogLoginDto dto) {
        IPage<LogLoginVo> page = logLoginService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询登录日志
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询登录日志", description = "查询登录日志", method = "GET")
    public Result<LogLogin> getById(@PathVariable Long id) {
        LogLogin vo = logLoginService.getById(id);
        return Result.success(vo);
    }

    /**
     * 查询登录日志
     *
     * @param dto 登录日志
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询登录日志", description = "查询登录日志", method = "GET")
    public Result<LogLoginVo> getOne(LogLoginDto dto) {
        LogLoginVo vo = logLoginService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 导出登录日志
     *
     * @param logLogin 登录日志
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出登录日志", description = "导出登录日志", method = "GET")
    public void exportExcel(LogLogin logLogin, HttpServletResponse response) {
        logLoginService.exportExcel(logLogin, response);
    }
}
