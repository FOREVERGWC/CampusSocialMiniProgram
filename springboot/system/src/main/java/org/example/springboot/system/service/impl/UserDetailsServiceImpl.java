package org.example.springboot.system.service.impl;

import jakarta.annotation.Resource;
import org.example.springboot.system.common.enums.AuthType;
import org.example.springboot.system.domain.entity.*;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.service.*;
import org.example.springboot.system.service.cache.ILoginCacheService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserService userService;
    @Resource
    private IUserAuthService userAuthService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IMenuService menuService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private ILoginCacheService loginCacheService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("登录失败！用户名或密码错误");
        }
        // 账户锁定
        boolean accountNonLocked = loginCacheService.getAccountNonLocked(username);
        // 角色列表
        List<Role> roleList = roleService.listByUserId(user.getId());
        // 菜单列表
        List<Menu> menuList = menuService.listByUserId(user.getId());
        // 权限列表
        List<Permission> permissionList = permissionService.listByUserId(user.getId());
        return new LoginUser(user, accountNonLocked, roleList, menuList, permissionList);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("登录失败！邮箱或验证码错误");
        }
        // 账户锁定
        boolean accountNonLocked = loginCacheService.getAccountNonLocked(email);
        // 角色列表
        List<Role> roleList = roleService.listByUserId(user.getId());
        // 菜单列表
        List<Menu> menuList = menuService.listByUserId(user.getId());
        // 权限列表
        List<Permission> permissionList = permissionService.listByUserId(user.getId());
        return new LoginUser(user, accountNonLocked, roleList, menuList, permissionList);
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        User user = userService.getByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("登录失败！手机号或验证码错误");
        }
        // 账户锁定
        boolean accountNonLocked = loginCacheService.getAccountNonLocked(phone);
        // 角色列表
        List<Role> roleList = roleService.listByUserId(user.getId());
        // 菜单列表
        List<Menu> menuList = menuService.listByUserId(user.getId());
        // 权限列表
        List<Permission> permissionList = permissionService.listByUserId(user.getId());
        return new LoginUser(user, accountNonLocked, roleList, menuList, permissionList);
    }

    public UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthService.getByAuthTypeAndOpenIdAnd(AuthType.WECHAT.getCode(), openId);
        if (userAuth == null) {
            throw new UsernameNotFoundException("登录失败！用户未绑定账号");
        }
        Long userId = userAuth.getUserId();
        User user = userService.getById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("登录失败！账号不存在");
        }
        // 账户锁定
        boolean accountNonLocked = loginCacheService.getAccountNonLocked(openId);
        // 角色列表
        List<Role> roleList = roleService.listByUserId(user.getId());
        // 菜单列表
        List<Menu> menuList = menuService.listByUserId(user.getId());
        // 权限列表
        List<Permission> permissionList = permissionService.listByUserId(user.getId());
        return new LoginUser(user, accountNonLocked, roleList, menuList, permissionList);
    }
}
