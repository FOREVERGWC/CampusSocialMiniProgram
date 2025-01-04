package org.example.springboot.system.domain.model;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.springboot.system.common.enums.EnableStatus;
import org.example.springboot.system.domain.entity.Menu;
import org.example.springboot.system.domain.entity.Permission;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 登录用户
 * </p>
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "登录用户实体", description = "登录用户")
public class LoginUser extends User implements UserDetails {
    /**
     * 账户锁定状态
     */
    @Schema(description = "账户锁定状态")
    private Boolean accountNonLocked;
    /**
     * 唯一标识符
     */
    @Schema(description = "唯一标识符")
    private String uuid;
    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;
    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<Role> roleList;
    /**
     * 菜单列表
     */
    @Schema(description = "菜单列表")
    private List<Menu> menuList;
    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<Permission> permissionList;

    public LoginUser(User user, Boolean accountNonLocked, List<Role> roleList, List<Menu> menuList, List<Permission> permissionList) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getNickname(), user.getName(), user.getAvatar(), user.getGender(), user.getBirthday(), user.getStatus(), user.getPhone(), user.getEmail(), user.getOpenId(), user.getBalance(), user.getLoginIp(), user.getLoginTime(), user.getCreateBy(), user.getCreateTime(), user.getUpdateBy(), user.getUpdateTime(), user.getRemark());
        this.accountNonLocked = accountNonLocked;
        this.roleList = roleList;
        this.menuList = menuList;
        this.permissionList = permissionList;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionList.stream()
                .map(Permission::getCode)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.getStatus(), EnableStatus.NORMAL.getCode());
    }
}
