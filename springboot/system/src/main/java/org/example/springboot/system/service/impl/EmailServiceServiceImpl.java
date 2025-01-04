package org.example.springboot.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.service.IEmailService;
import org.example.springboot.system.service.IUserService;
import org.example.springboot.system.service.cache.ICaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceServiceImpl implements IEmailService {
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private ICaptchaService captchaService;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private IUserService userService;

    @Value("${spring.application.name}")
    private String application;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendRegisterCode(String email) {
        User user = userService.getByEmail(email);
        if (user != null) {
            throw new ServiceException(ResultCode.EMAIL_HAS_USED_ERROR);
        }
        String captcha = captchaService.getEmailRegisterCode(email);
        if (StrUtil.isNotBlank(captcha)) {
            throw new ServiceException(ResultCode.EMAIL_HAS_SEND_ERROR);
        }
        String subject = "注册";
        String code = RandomUtil.randomNumbers(4);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("application", application);
        dataMap.put("email", email);
        dataMap.put("code", code);
        dataMap.put("createTime", DateUtil.now());
        Context context = new Context();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        String content = templateEngine.process("register", context);
        try {
            sendEmail(email, subject, content);
            captchaService.setEmailRegisterCode(email, code);
            log.info("【{}】邮箱：{}邮件：{}发送成功！", subject, email, code);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("【{}】邮箱：{}邮件：{}发送失败！错误信息：{}", subject, email, code, e.toString());
        }
    }

    @Override
    public void sendResetCode(String email) {
        String captcha = captchaService.getEmailResetCode(email);
        if (StrUtil.isNotBlank(captcha)) {
            throw new ServiceException(ResultCode.EMAIL_HAS_SEND_ERROR);
        }
        String subject = "修改密码";
        String code = RandomUtil.randomNumbers(4);
        try {
            sendEmail(email, subject, StrUtil.format("改密验证码：{}", code));
            captchaService.setEmailResetCode(email, code);
            log.info("【{}】邮箱：{}邮件：{}发送成功！", subject, email, code);
        } catch (Exception e) {
            log.error("【{}】邮箱：{}邮件：{}发送失败！错误信息：{}", subject, email, code, e.toString());
        }
    }

    @Override
    public void sendLoginCode(String email) {
        String captcha = captchaService.getEmailLoginCode(email);
        if (StrUtil.isNotBlank(captcha)) {
            throw new ServiceException(ResultCode.EMAIL_HAS_SEND_ERROR);
        }
        String subject = "登录";
        String code = RandomUtil.randomNumbers(4);
        try {
            sendEmail(email, subject, StrUtil.format("登录验证码：{}", code));
            captchaService.setEmailLoginCode(email, code);
            log.info("【{}】邮箱：{}邮件：{}发送成功！", subject, email, code);
        } catch (Exception e) {
            log.error("【{}】邮箱：{}邮件：{}发送失败！错误信息：{}", subject, email, code, e.toString());
        }
    }

    /**
     * 发送邮件
     *
     * @param email   邮箱
     * @param subject 主题
     * @param content 内容
     */
    private void sendEmail(String email, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(username, application);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
