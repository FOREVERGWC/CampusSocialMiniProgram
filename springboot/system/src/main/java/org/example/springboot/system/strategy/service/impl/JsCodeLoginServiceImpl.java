package org.example.springboot.system.strategy.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.common.config.security.WechatOpenIdAuthenticationToken;
import org.example.springboot.system.common.enums.LoginType;
import org.example.springboot.system.common.manager.AsyncManager;
import org.example.springboot.system.common.manager.factory.AsyncFactory;
import org.example.springboot.system.domain.model.LoginBody;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.domain.model.WechatResponse;
import org.example.springboot.system.service.ITokenService;
import org.example.springboot.system.service.cache.ILoginCacheService;
import org.example.springboot.system.strategy.service.ILoginService;
import org.example.springboot.system.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信授权登录服务类
 * </p>
 */
@Slf4j
@Service
public class JsCodeLoginServiceImpl implements ILoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private ILoginCacheService loginCacheService;
    @Resource
    private ITokenService tokenService;

    @Value("${auth.wechat.appId}")
    private String appId;
    @Value("${auth.wechat.secret}")
    private String secret;

    @Override
    public String login(LoginBody body) {
        Authentication authentication;
        boolean flag = false;
        Exception exception = new RuntimeException();

        WechatResponse response = WechatUtils.getResponseByJsCode(appId, secret, body.getJsCode());
        String openId = body.getJsCode();
        try {
            if (response == null) {
                throw new ServiceException(ResultCode.LOGIN_CODE_ERROR);
            }
            openId = response.getOpenid();
            WechatOpenIdAuthenticationToken authenticationToken = new WechatOpenIdAuthenticationToken(openId);
            authentication = authenticationManager.authenticate(authenticationToken);
            flag = true;
        } catch (AccountExpiredException e) {
            // 账号过期
            exception = e;
            throw e;
        } catch (CredentialsExpiredException e) {
            // 密码过期
            exception = e;
            throw e;
        } catch (DisabledException e) {
            // 账号被禁用
            exception = e;
            throw e;
        } catch (LockedException e) {
            // 账号被锁定
            exception = e;
            throw e;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            // 用户名或密码错误
            exception = e;
            throw e;
        } catch (InternalAuthenticationServiceException e) {
            // 系统内部错误
            exception = e;
            throw e;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            if (flag) {
                AsyncManager.me().execute(AsyncFactory.recordLogin(openId, LoginType.WECHAT_OPENID, true, Result.success().getMsg()));
            } else {
                loginCacheService.addFailureCount(openId);
                AsyncManager.me().execute(AsyncFactory.recordLogin(openId, LoginType.WECHAT_OPENID, false, exception.getMessage()));
            }
        }
        LoginUser user = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(user);
        AsyncManager.me().execute(AsyncFactory.updateLogin(user.getId()));
        return token;
    }
}
