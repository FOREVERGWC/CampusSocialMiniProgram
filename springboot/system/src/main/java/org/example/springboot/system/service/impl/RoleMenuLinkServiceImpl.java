package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.RoleMenuLinkDto;
import org.example.springboot.system.domain.entity.Menu;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.RoleMenuLink;
import org.example.springboot.system.domain.vo.RoleMenuLinkVo;
import org.example.springboot.system.mapper.MenuMapper;
import org.example.springboot.system.mapper.RoleMapper;
import org.example.springboot.system.mapper.RoleMenuLinkMapper;
import org.example.springboot.system.service.IRoleMenuLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色、菜单关系服务实现类
 * </p>
 */
@Service
public class RoleMenuLinkServiceImpl extends ServiceImpl<RoleMenuLinkMapper, RoleMenuLink> implements IRoleMenuLinkService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;

    @Transactional
    @Override
    public void saveBatchByRoleIdAndMenuIds(Long roleId, List<Long> menuIds) {
        lambdaUpdate()
                .eq(RoleMenuLink::getRoleId, roleId)
                .remove();
        if (CollectionUtil.isEmpty(menuIds)) {
            return;
        }
        List<RoleMenuLink> linkList = menuIds.stream()
                .<RoleMenuLink>map(item -> RoleMenuLink.builder()
                        .roleId(roleId)
                        .menuId(item)
                        .build())
                .toList();
        saveBatch(linkList);
    }

    @Override
    public void removeByRoleId(Serializable roleId) {
        lambdaUpdate()
                .eq(RoleMenuLink::getRoleId, roleId)
                .remove();
    }

    @Override
    public void removeByRoleIds(Collection<?> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return;
        }
        lambdaUpdate()
                .in(RoleMenuLink::getRoleId, roleIds)
                .remove();
    }

    @Override
    public void removeByMenuId(Serializable menuId) {
        lambdaUpdate()
                .eq(RoleMenuLink::getMenuId, menuId)
                .remove();
    }

    @Override
    public void removeByMenuIds(Collection<?> menuIds) {
        if (CollectionUtil.isEmpty(menuIds)) {
            return;
        }
        lambdaUpdate()
                .in(RoleMenuLink::getMenuId, menuIds)
                .remove();
    }

    @Override
    public List<RoleMenuLink> listByRoleId(Serializable roleId) {
        return Optional.ofNullable(lambdaQuery()
                        .eq(RoleMenuLink::getRoleId, roleId)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<RoleMenuLink> listByRoleIds(Collection<?> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return List.of();
        }
        return Optional.ofNullable(lambdaQuery()
                        .in(RoleMenuLink::getRoleId, roleIds)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<RoleMenuLink> listByMenuId(Serializable menuId) {
        return Optional.ofNullable(lambdaQuery()
                        .eq(RoleMenuLink::getMenuId, menuId)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<RoleMenuLink> listByMenuIds(Collection<?> menuIds) {
        if (CollectionUtil.isEmpty(menuIds)) {
            return List.of();
        }
        return Optional.ofNullable(lambdaQuery()
                        .in(RoleMenuLink::getMenuId, menuIds)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        List<RoleMenuLink> linkList = listByRoleId(roleId);
        return linkList.stream().map(RoleMenuLink::getMenuId).distinct().toList();
    }

    @Override
    public List<Long> listMenuIdsByRoleIds(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return List.of();
        }
        List<RoleMenuLink> linkList = listByRoleIds(roleIds);
        return linkList.stream().map(RoleMenuLink::getMenuId).distinct().toList();
    }

    @Override
    public List<Long> listRoleIdsByMenuId(Long menuId) {
        List<RoleMenuLink> linkList = listByMenuId(menuId);
        return linkList.stream().map(RoleMenuLink::getRoleId).distinct().toList();
    }

    @Override
    public List<Long> listRoleIdsByMenuIds(List<Long> menuIds) {
        if (CollectionUtil.isEmpty(menuIds)) {
            return List.of();
        }
        List<RoleMenuLink> linkList = listByMenuIds(menuIds);
        return linkList.stream().map(RoleMenuLink::getRoleId).distinct().toList();
    }

    @Override
    public List<RoleMenuLinkVo> getList(RoleMenuLinkDto dto) {
        List<RoleMenuLink> roleMenuLinkList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(roleMenuLinkList)) {
            return List.of();
        }
        // 角色
        List<Long> roleIdList = roleMenuLinkList.stream().map(RoleMenuLink::getRoleId).toList();
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, roleIdList));
        Map<Long, Role> roleMap = roleList.stream().collect(Collectors.toMap(Role::getId, item -> item));
        // 菜单
        List<Long> menuIdList = roleMenuLinkList.stream().map(RoleMenuLink::getMenuId).toList();
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIdList));
        Map<Long, Menu> menuMap = menuList.stream().collect(Collectors.toMap(Menu::getId, item -> item));
        // 组装VO
        return roleMenuLinkList.stream().map(item -> {
            RoleMenuLinkVo vo = new RoleMenuLinkVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRole(roleMap.getOrDefault(item.getRoleId(), Role.builder().name("已删除").build()));
            vo.setMenu(menuMap.getOrDefault(item.getMenuId(), Menu.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RoleMenuLinkVo> getPage(RoleMenuLinkDto dto) {
        Page<RoleMenuLink> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 角色
        List<Long> roleIdList = info.getRecords().stream().map(RoleMenuLink::getRoleId).toList();
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, roleIdList));
        Map<Long, Role> roleMap = roleList.stream().collect(Collectors.toMap(Role::getId, item -> item));
        // 菜单
        List<Long> menuIdList = info.getRecords().stream().map(RoleMenuLink::getMenuId).toList();
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIdList));
        Map<Long, Menu> menuMap = menuList.stream().collect(Collectors.toMap(Menu::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            RoleMenuLinkVo vo = new RoleMenuLinkVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRole(roleMap.getOrDefault(item.getRoleId(), Role.builder().name("已删除").build()));
            vo.setMenu(menuMap.getOrDefault(item.getMenuId(), Menu.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public RoleMenuLinkVo getOne(RoleMenuLinkDto dto) {
        RoleMenuLink one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 角色
        Role role = Optional.ofNullable(roleMapper.selectById(one.getRoleId())).orElse(Role.builder().name("已删除").build());
        // 菜单
        Menu menu = Optional.ofNullable(menuMapper.selectById(one.getMenuId())).orElse(Menu.builder().name("已删除").build());
        // 组装VO
        RoleMenuLinkVo vo = new RoleMenuLinkVo();
        BeanUtils.copyProperties(one, vo);
        vo.setRole(role);
        vo.setMenu(menu);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 角色、菜单关系
     * @return 结果
     */
    private LambdaQueryChainWrapper<RoleMenuLink> getWrapper(RoleMenuLink entity) {
        LambdaQueryChainWrapper<RoleMenuLink> wrapper = lambdaQuery()
                .eq(entity.getId() != null, RoleMenuLink::getId, entity.getId())
                .eq(entity.getRoleId() != null, RoleMenuLink::getRoleId, entity.getRoleId())
                .eq(entity.getMenuId() != null, RoleMenuLink::getMenuId, entity.getMenuId());
        if (entity instanceof RoleMenuLinkDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    RoleMenuLink::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
