package org.example.springboot.system.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.example.springboot.common.common.Constants;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.service.ITokenService;
import org.example.springboot.system.service.cache.ILoginCacheService;
import org.example.springboot.system.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 令牌服务实现类
 * </p>
 */
@Service
public class TokenServiceImpl implements ITokenService {
    @Resource
    private ILoginCacheService loginCacheService;

    @Override
    public String createToken(LoginUser user) {
        String uuid = UUID.fastUUID().toString();
        user.setUuid(uuid);
        loginCacheService.setLoginUser(user);
        return TokenUtils.createToken(uuid);
    }

    @Override
    public String getAuthorization(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(Constants.TOKEN);
        }
        if (StrUtil.isNotBlank(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        String authorization = getAuthorization(request);
        if (StrUtil.isBlank(authorization)) {
            throw new ServiceException(ResultCode.TOKEN_CHECK_ERROR);
        }
        TokenUtils.verifyToken(authorization);
        LoginUser user = loginCacheService.getLoginUser(authorization);
        user.setToken(authorization);
        return user;
    }
}
