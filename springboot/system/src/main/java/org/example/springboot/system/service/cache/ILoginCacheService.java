package org.example.springboot.system.service.cache;

import org.example.springboot.system.domain.model.LoginUser;

public interface ILoginCacheService {
    /**
     * 记录登录失败次数
     *
     * @param principal 用户身份
     */
    void addFailureCount(String principal);

    /**
     * 获取账户锁定状态
     *
     * @param principal 用户身份
     */
    boolean getAccountNonLocked(String principal);

    /**
     * 存入登录用户
     *
     * @param user 登录用户
     */
    void setLoginUser(LoginUser user);

    /**
     * 移除登录用户
     *
     * @param uuid 唯一标识符
     */
    void removeLoginUser(String uuid);

    /**
     * 通过令牌获取登录用户
     *
     * @param token 令牌
     * @return 登录用户
     */
    LoginUser getLoginUser(String token);

    /**
     * 通过令牌获取登录用户ID
     *
     * @param token 令牌
     * @return 登录用户ID
     */
    Long getUserIdByToken(String token);

    /**
     * 通过令牌获取登录用户名
     *
     * @param token 令牌
     * @return 登录用户名
     */
    String getUsernameByToken(String token);
}
