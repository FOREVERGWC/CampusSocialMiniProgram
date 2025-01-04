package org.example.springboot.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.model.EmailBody;
import org.example.springboot.system.service.IEmailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 邮件服务前端控制器
 * </p>
 */
@RestController
@RequestMapping("/email")
@Tag(name = "邮件服务", description = "邮件服务")
public class EmailController {
    @Resource
    private IEmailService emailService;

    /**
     * 根据邮箱发送注册验证码邮件
     *
     * @param body 注册邮箱
     * @return 结果
     */
    @PostMapping("/register")
    @Operation(summary = "根据邮箱发送注册验证码邮件", description = "根据邮箱发送注册验证码邮件", method = "POST")
    public Result<Void> sendRegisterCode(@Validated @RequestBody EmailBody body) {
        emailService.sendRegisterCode(body.getEmail());
        return Result.success();
    }

    /**
     * 根据邮箱发送改密验证码邮件
     *
     * @param body 注册邮箱
     * @return 结果
     */
    @PostMapping("/reset")
    @Operation(summary = "根据邮箱发送改密验证码邮件", description = "根据邮箱发送改密验证码邮件", method = "POST")
    public Result<Void> sendResetCode(@Validated @RequestBody EmailBody body) {
        emailService.sendResetCode(body.getEmail());
        return Result.success();
    }

    /**
     * 根据邮箱发送登录验证码邮件
     *
     * @param body 注册邮箱
     * @return 结果
     */
    @PostMapping("/login")
    @Operation(summary = "根据邮箱发送登录验证码邮件", description = "根据邮箱发送登录验证码邮件", method = "POST")
    public Result<Void> sendLoginCode(@Validated @RequestBody EmailBody body) {
        emailService.sendLoginCode(body.getEmail());
        return Result.success();
    }
}
