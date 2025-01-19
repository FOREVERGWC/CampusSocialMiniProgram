package org.example.springboot.system.strategy.service;

import org.example.springboot.system.domain.model.LoginBody;

/**
 * <p>
 * 登录服务类
 * </p>
 */
public interface ILoginService {
    /**
     * 登录
     *
     * @param body 登录请求体
     * @return 结果
     */
    String login(LoginBody body);
}
