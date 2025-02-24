package org.example.springboot.system.strategy.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.common.enums.AuthType;
import org.example.springboot.system.common.enums.LoginType;
import org.example.springboot.system.common.manager.AsyncManager;
import org.example.springboot.system.common.manager.factory.AsyncFactory;
import org.example.springboot.system.domain.entity.UserAuth;
import org.example.springboot.system.domain.model.LoginBody;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.domain.model.WechatResponse;
import org.example.springboot.system.service.ITokenService;
import org.example.springboot.system.service.IUserAuthService;
import org.example.springboot.system.service.cache.ICaptchaService;
import org.example.springboot.system.service.cache.ILoginCacheService;
import org.example.springboot.system.strategy.service.ILoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 账密登录服务类
 * </p>
 */
@Slf4j
@Service
public class UsernamePasswordLoginServiceImpl implements ILoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private ICaptchaService captchaService;
    @Resource
    private ILoginCacheService loginCacheService;
    @Resource
    private ITokenService tokenService;
    @Resource
    private IUserAuthService userAuthService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("${auth.wechat.appId}")
    private String appId;
    @Value("${auth.wechat.secret}")
    private String secret;

    @Override
    public String login(LoginBody body) {
        Authentication authentication;
        boolean flag = false;
        Exception exception = new RuntimeException();
        String username = body.getUsername();
        try {
            captchaService.validateUuidLoginCode(body.getUuid(), body.getCode());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, body.getPassword());
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
            exception = new ServiceException(ResultCode.LOGIN_USERNAME_OR_PASSWORD_ERROR);
            throw new ServiceException(ResultCode.LOGIN_USERNAME_OR_PASSWORD_ERROR);
        } catch (InternalAuthenticationServiceException e) {
            // 系统内部错误
            exception = e;
            throw e;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            if (flag) {
                AsyncManager.me().execute(AsyncFactory.recordLogin(username, LoginType.USERNAME_PASSWORD, true, Result.success().getMsg()));
            } else {
                loginCacheService.addFailureCount(username);
                AsyncManager.me().execute(AsyncFactory.recordLogin(username, LoginType.USERNAME_PASSWORD, false, exception.getMessage()));
            }
        }
        LoginUser user = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(user);
        AsyncManager.me().execute(AsyncFactory.updateLogin(user.getId()));
        // 绑定微信
        threadPoolTaskExecutor.execute(() -> {
            if (body.getJsCode() == null) {
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("appid", appId);
            map.put("secret", secret);
            map.put("js_code", body.getJsCode());
            map.put("grant_type", "authorization_code");

            // TODO 使用HTTP线程池代替Hutool(未使用线程池，频繁创建实例性能损耗)
            try (HttpResponse response = HttpUtil.createGet("https://api.weixin.qq.com/sns/jscode2session").form(map).execute()) {
                String result = response.body();
                if (!response.isOk()) {
                    log.error("用户【{}】绑定微信【{}】失败！响应错误：{}", username, body.getJsCode(), result);
                    return;
                }
                WechatResponse wechatResponse = JSONUtil.toBean(result, WechatResponse.class);
                if (wechatResponse == null || wechatResponse.getErrcode() != null) {
                    log.error("用户【{}】绑定微信【{}】失败！解析错误：{}", username, body.getJsCode(), wechatResponse);
                    return;
                }

                boolean exists = userAuthService.lambdaQuery()
                        .eq(UserAuth::getUserId, user.getId())
                        .eq(UserAuth::getAuthType, AuthType.WECHAT.getCode())
                        .exists();

                if (!exists) {
                    UserAuth userAuth = UserAuth.builder().build();
                    userAuth.setUserId(user.getId());
                    userAuth.setAuthType(AuthType.WECHAT.getCode());
                    userAuth.setOpenId(wechatResponse.getOpenid());
                    userAuth.setAccessToken(wechatResponse.getUnionid() == null ? "" : wechatResponse.getUnionid());
                    userAuthService.save(userAuth);
                }
            }
        });
        return token;
    }
}
