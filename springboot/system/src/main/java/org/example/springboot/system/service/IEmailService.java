package org.example.springboot.system.service;

public interface IEmailService {
    /**
     * 根据邮箱发送注册验证码邮件
     *
     * @param email 邮箱
     */
    void sendRegisterCode(String email);

    /**
     * 根据邮箱发送改密验证码邮件
     *
     * @param email 邮箱
     */
    void sendResetCode(String email);

    /**
     * 根据邮箱发送登录验证码邮件
     *
     * @param email 邮箱
     */
    void sendLoginCode(String email);
}
