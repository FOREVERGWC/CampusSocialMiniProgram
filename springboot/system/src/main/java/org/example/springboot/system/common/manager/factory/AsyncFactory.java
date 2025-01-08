package org.example.springboot.system.common.manager.factory;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.useragent.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.system.common.enums.LoginType;
import org.example.springboot.system.domain.entity.LogLogin;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.service.impl.LogLoginServiceImpl;
import org.example.springboot.system.service.impl.UserServiceImpl;
import org.example.springboot.common.utils.AddressUtils;
import org.example.springboot.common.utils.ServletUtils;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * 异步工厂
 */
@Slf4j
public class AsyncFactory {
    /**
     * 记录登录日志
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @param status    状态
     * @param msg       消息
     * @return 结果
     */
    public static TimerTask recordLogin(String username, LoginType loginType, Boolean status, String msg) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent ua = ServletUtils.getUserAgent(request);
        String ip = ServletUtils.getUserIp(request);

        return new TimerTask() {
            @Override
            public void run() {
                String location = AddressUtils.getRealAddressByIP(ip);
                LogLogin logLogin = LogLogin.builder()
                        .loginType(loginType.getCode())
                        .os(ua.getOs().getName())
                        .browser(ua.getBrowser().getName())
                        .ip(ip)
                        .location(location)
                        .status(status)
                        .msg(msg)
                        .createBy(username)
                        .updateBy(username)
                        .build();
                SpringUtil.getBean(LogLoginServiceImpl.class).save(logLogin);
            }
        };
    }

    /**
     * 记录退出日志
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @param status    状态
     * @param msg       消息
     * @return 结果
     */
    public static TimerTask recordLogout(String username, LoginType loginType, Boolean status, String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                log.info("记录退出登录：{}，{}", username, LocalDateTime.now());
            }
        };
    }

    /**
     * 记录操作日志
     *
     * @return 结果
     */
    public static TimerTask recordOperation() {
        return new TimerTask() {
            @Override
            public void run() {
                log.info("记录操作日志：{}", LocalDateTime.now());
            }
        };
    }

    public static TimerTask updateLogin(Long userId) {
        String ip = ServletUtils.getUserIp();
        LocalDateTime now = LocalDateTime.now();

        return new TimerTask() {
            @Override
            public void run() {
                User user = User.builder().id(userId).loginIp(ip).loginTime(now).build();
                SpringUtil.getBean(UserServiceImpl.class).updateById(user);
            }
        };
    }
}
