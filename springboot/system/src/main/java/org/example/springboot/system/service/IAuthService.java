package org.example.springboot.system.service;

import org.example.springboot.system.domain.model.LoginBody;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.domain.model.RegisterBody;
import org.example.springboot.system.domain.model.ResetBody;
import org.example.springboot.system.domain.vo.CaptchaVo;
import org.example.springboot.system.domain.vo.RouteVo;

import java.util.List;

public interface IAuthService {
    /**
     * 获取验证码图片
     *
     * @return 结果
     */
    CaptchaVo getCaptcha();

    /**
     * 登录
     *
     * @param body 登录请求体
     * @return 结果
     */
    String login(LoginBody body);

    /**
     * 退出
     *
     * @param user 登录用户
     */
    void logout(LoginUser user);

    /**
     * 注册用户
     *
     * @param body PC端注册请求体
     */
    void register(RegisterBody body);

    /**
     * 重置密码
     *
     * @param body 密码信息
     */
    void resetPassword(ResetBody body);

    /**
     * 获取当前用户路由信息
     *
     * @return 结果
     */
    List<RouteVo> getRoute();
}
