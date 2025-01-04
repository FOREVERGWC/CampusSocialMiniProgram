package org.example.springboot.system.common.config.security;

import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.system.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 邮箱验证码校验提供器
 */
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;

    public EmailCodeAuthenticationProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        EmailCodeAuthenticationToken token = (EmailCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByEmail((String) token.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException(ResultCode.LOGIN_EMAIL_CODE_ERROR.getMsg());
        }
        EmailCodeAuthenticationToken result = new EmailCodeAuthenticationToken(user, user.getAuthorities());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
