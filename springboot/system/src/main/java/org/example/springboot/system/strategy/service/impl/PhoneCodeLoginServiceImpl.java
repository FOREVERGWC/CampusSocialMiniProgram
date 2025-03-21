package org.example.springboot.system.strategy.service.impl;

import jakarta.annotation.Resource;
import org.example.springboot.system.common.config.security.PhoneCodeAuthenticationToken;
import org.example.springboot.system.common.enums.LoginType;
import org.example.springboot.system.common.manager.AsyncManager;
import org.example.springboot.system.common.manager.factory.AsyncFactory;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.model.LoginBody;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.service.ITokenService;
import org.example.springboot.system.service.cache.ICaptchaService;
import org.example.springboot.system.service.cache.ILoginCacheService;
import org.example.springboot.system.strategy.service.ILoginService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 手机验证码登录服务类
 * </p>
 */
@Service
public class PhoneCodeLoginServiceImpl implements ILoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private ICaptchaService captchaService;
    @Resource
    private ILoginCacheService loginCacheService;
    @Resource
    private ITokenService tokenService;

    @Override
    public String login(LoginBody body) {
        Authentication authentication;
        boolean flag = false;
        Exception exception = new RuntimeException();
        String phone = body.getPhone();
        try {
            captchaService.validatePhoneLoginCode(phone, body.getCode());
            PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(phone);
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
                AsyncManager.me().execute(AsyncFactory.recordLogin(phone, LoginType.PHONE_CODE, true, Result.success().getMsg()));
            } else {
                loginCacheService.addFailureCount(phone);
                AsyncManager.me().execute(AsyncFactory.recordLogin(phone, LoginType.PHONE_CODE, false, exception.getMessage()));
            }
        }
        LoginUser user = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(user);
        AsyncManager.me().execute(AsyncFactory.updateLogin(user.getId()));
        return token;
    }
}
