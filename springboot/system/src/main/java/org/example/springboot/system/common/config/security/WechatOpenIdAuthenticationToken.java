package org.example.springboot.system.common.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 唯一标识校验令牌
 */
public class WechatOpenIdAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;

    public WechatOpenIdAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        this.setAuthenticated(false);
    }

    public WechatOpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
