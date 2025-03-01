package org.example.springboot.system.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.common.instance.ObjectMapperInstance;
import org.example.springboot.common.domain.Result;
import org.example.springboot.common.utils.ServletUtils;
import org.example.springboot.system.common.manager.AsyncManager;
import org.example.springboot.system.common.manager.factory.AsyncFactory;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.service.ITokenService;
import org.example.springboot.system.service.IUserAuthService;
import org.example.springboot.system.service.cache.ILoginCacheService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 退出成功处理器
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    private ITokenService tokenService;
    @Resource
    private ILoginCacheService loginCacheService;
    @Resource
    private IUserAuthService userAuthService;

    ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // TODO 移到AuthService，整理逻辑，解决循环依赖
        LoginUser user = tokenService.getLoginUser(request);
        loginCacheService.removeLoginUser(user.getUuid());
        userAuthService.removeByUserId(user.getId());
        AsyncManager.me().execute(AsyncFactory.recordLogout(user.getUsername(), null, true, "退出成功！"));
        ServletUtils.write(response, objectMapper.writeValueAsString(Result.success()));
    }
}
