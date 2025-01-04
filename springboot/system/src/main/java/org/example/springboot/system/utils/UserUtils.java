package org.example.springboot.system.utils;

import org.example.springboot.system.domain.model.LoginUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class UserUtils {
    /**
     * 获取授权信息
     *
     * @return 授权信息
     */
    public static Authentication getAuthentication() {
        try {
            return SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户
     *
     * @return 用户
     */
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getLoginUserId() {
        try {
            return getLoginUser().getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    public static String getLoginUsername() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication.getName();
    }

    /**
     * 获取当前用户IP
     *
     * @return IP
     */
    public static String getLoginIP() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
    }
}
