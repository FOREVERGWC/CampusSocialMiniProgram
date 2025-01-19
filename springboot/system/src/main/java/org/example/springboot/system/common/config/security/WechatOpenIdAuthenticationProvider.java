package org.example.springboot.system.common.config.security;

import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.system.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 唯一标识校验提供器
 */
public class WechatOpenIdAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;

    public WechatOpenIdAuthenticationProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        WechatOpenIdAuthenticationToken token = (WechatOpenIdAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByOpenId((String) token.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException(ResultCode.LOGIN_WECHAT_OPENID_ERROR.getMsg());
        }
        WechatOpenIdAuthenticationToken result = new WechatOpenIdAuthenticationToken(user, user.getAuthorities());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatOpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
