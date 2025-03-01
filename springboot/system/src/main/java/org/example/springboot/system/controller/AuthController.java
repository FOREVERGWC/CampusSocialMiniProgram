package org.example.springboot.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.model.LoginBody;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.domain.model.RegisterBody;
import org.example.springboot.system.domain.model.reset.ResetBody;
import org.example.springboot.system.domain.model.reset.ResetEmailBody;
import org.example.springboot.system.domain.vo.CaptchaVo;
import org.example.springboot.system.domain.vo.RouteVo;
import org.example.springboot.system.service.IAuthService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 网站服务前端控制器
 * </p>
 */
@RestController
@Tag(name = "网站服务", description = "网站服务")
public class AuthController {
    @Resource
    private IAuthService authService;

    /**
     * 登录
     *
     * @param body 登录请求体
     * @return 结果
     */
    @PostMapping("/login")
    @Operation(summary = "登录", description = "登录", method = "POST")
    public Result<String> login(@RequestBody LoginBody body) {
        String token = authService.login(body);
        return Result.success("登录成功！", token);
    }

    /**
     * 登出
     *
     * @return 结果
     */
    @PostMapping("/logout")
    @Operation(summary = "登出", description = "登出", method = "POST")
    public Result<String> logout() {
        LoginUser user = UserUtils.getLoginUser();
        authService.logout(user);
        return Result.success();
    }

    /**
     * 注册用户
     *
     * @param body PC端注册请求体
     * @return 结果
     */
    @PostMapping("/register")
    @Operation(summary = "注册用户", description = "注册用户", method = "POST")
    public Result<Void> register(@Validated @RequestBody RegisterBody body) {
        // TODO @Validated失效，不校验入参
        authService.register(body);
        return Result.success();
    }

    /**
     * 重置密码
     *
     * @param body 密码信息
     * @return 结果
     */
    @PutMapping("/password/reset")
    @Operation(summary = "重置密码", description = "重置密码", method = "PUT")
    public Result<Void> resetPassword(@Validated @RequestBody ResetBody body) {
        authService.resetPassword(body);
        return Result.success();
    }

    /**
     * 修改邮箱
     *
     * @param body 邮箱信息
     * @return 结果
     */
    @PutMapping("/email/update")
    @Operation(summary = "修改邮箱", description = "修改邮箱", method = "PUT")
    public Result<Void> updateEmail(@Validated @RequestBody ResetEmailBody body) {
        authService.updateEmail(body);
        return Result.success();
    }

    /**
     * 获取验证码图片
     *
     * @return 结果
     */
    @GetMapping("/captcha")
    @Operation(summary = "获取验证码图片", description = "获取验证码图片", method = "GET")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo vo = authService.getCaptcha();
        return Result.success(vo);
    }

    /**
     * 获取当前用户信息
     *
     * @return 结果
     */
    @GetMapping("/token")
    @Operation(summary = "获取当前用户信息", description = "获取当前用户信息", method = "GET")
    public Result<LoginUser> getByToken() {
        LoginUser user = UserUtils.getLoginUser();
        return Result.success(user);
    }

    /**
     * 获取当前用户路由信息
     *
     * @return 结果
     */
    @GetMapping("/route")
    @Operation(summary = "获取当前用户路由信息", description = "获取当前用户路由信息", method = "GET")
    public Result<List<RouteVo>> getRoute() {
        List<RouteVo> list = authService.getRoute();
        return Result.success(list);
    }
}
