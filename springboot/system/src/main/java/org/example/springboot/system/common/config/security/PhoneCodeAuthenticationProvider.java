package org.example.springboot.system.common.config.security;

import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.system.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 手机验证码校验提供器
 */
public class PhoneCodeAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;

    public PhoneCodeAuthenticationProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        PhoneCodeAuthenticationToken token = (PhoneCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByPhone((String) token.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException(ResultCode.LOGIN_PHONE_CODE_ERROR.getMsg());
        }
        PhoneCodeAuthenticationToken result = new PhoneCodeAuthenticationToken(user, user.getAuthorities());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
