package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.RolePermissionLinkDto;
import org.example.springboot.system.domain.entity.Permission;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.RolePermissionLink;
import org.example.springboot.system.domain.vo.RolePermissionLinkVo;
import org.example.springboot.system.mapper.PermissionMapper;
import org.example.springboot.system.mapper.RoleMapper;
import org.example.springboot.system.mapper.RolePermissionLinkMapper;
import org.example.springboot.system.service.IRolePermissionLinkService;
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
 * 角色、权限关系服务实现类
 * </p>
 */
@Service
public class RolePermissionLinkServiceImpl extends ServiceImpl<RolePermissionLinkMapper, RolePermissionLink> implements IRolePermissionLinkService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    @Transactional
    @Override
    public void saveBatchByRoleIdAndPermissionIds(Long roleId, List<Long> permissionIds) {
        lambdaUpdate()
                .eq(RolePermissionLink::getRoleId, roleId)
                .remove();
        if (CollectionUtil.isEmpty(permissionIds)) {
            return;
        }
        List<RolePermissionLink> linkList = permissionIds.stream()
                .<RolePermissionLink>map(item -> RolePermissionLink.builder()
                        .roleId(roleId)
                        .permissionId(item)
                        .build())
                .toList();
        saveBatch(linkList);
    }

    @Override
    public void removeByRoleId(Serializable roleId) {
        lambdaUpdate()
                .eq(RolePermissionLink::getRoleId, roleId)
                .remove();
    }

    @Override
    public void removeByRoleIds(Collection<?> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return;
        }
        lambdaUpdate()
                .in(RolePermissionLink::getRoleId, roleIds)
                .remove();
    }

    @Override
    public void removeByPermissionId(Serializable permissionId) {
        lambdaUpdate()
                .eq(RolePermissionLink::getPermissionId, permissionId)
                .remove();
    }

    @Override
    public void removeByPermissionIds(Collection<?> permissionIds) {
        if (CollectionUtil.isEmpty(permissionIds)) {
            return;
        }
        lambdaUpdate()
                .in(RolePermissionLink::getPermissionId, permissionIds)
                .remove();
    }

    @Override
    public List<RolePermissionLink> listByRoleId(Serializable roleId) {
        return Optional.ofNullable(lambdaQuery()
                        .eq(RolePermissionLink::getRoleId, roleId)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<RolePermissionLink> listByRoleIds(Collection<?> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return List.of();
        }
        return Optional.ofNullable(lambdaQuery()
                        .in(RolePermissionLink::getRoleId, roleIds)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<RolePermissionLink> listByPermissionId(Serializable permissionId) {
        return Optional.ofNullable(lambdaQuery()
                        .eq(RolePermissionLink::getPermissionId, permissionId)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<RolePermissionLink> listByPermissionIds(Collection<?> permissionIds) {
        if (CollectionUtil.isEmpty(permissionIds)) {
            return List.of();
        }
        return Optional.ofNullable(lambdaQuery()
                        .in(RolePermissionLink::getPermissionId, permissionIds)
                        .list())
                .orElse(List.of());
    }

    @Override
    public List<Long> listPermissionIdsByRoleId(Long roleId) {
        List<RolePermissionLink> linkList = listByRoleId(roleId);
        return linkList.stream().map(RolePermissionLink::getPermissionId).distinct().toList();
    }

    @Override
    public List<Long> listPermissionIdsByRoleIds(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return List.of();
        }
        List<RolePermissionLink> linkList = listByRoleIds(roleIds);
        return linkList.stream().map(RolePermissionLink::getPermissionId).distinct().toList();
    }

    @Override
    public List<Long> listRoleIdsByPermissionId(Long permissionId) {
        List<RolePermissionLink> linkList = listByPermissionId(permissionId);
        return linkList.stream().map(RolePermissionLink::getRoleId).distinct().toList();
    }

    @Override
    public List<Long> listRoleIdsByPermissionIds(List<Long> permissionIds) {
        if (CollectionUtil.isEmpty(permissionIds)) {
            return List.of();
        }
        List<RolePermissionLink> linkList = listByPermissionIds(permissionIds);
        return linkList.stream().map(RolePermissionLink::getRoleId).distinct().toList();
    }

    @Override
    public List<RolePermissionLinkVo> getList(RolePermissionLinkDto dto) {
        List<RolePermissionLink> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 角色
        List<Long> roleIdList = list.stream().map(RolePermissionLink::getRoleId).toList();
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, roleIdList));
        Map<Long, Role> roleMap = roleList.stream().collect(Collectors.toMap(Role::getId, item -> item));
        // 权限
        List<Long> permissionIdList = list.stream().map(RolePermissionLink::getPermissionId).toList();
        List<Permission> permissionList = permissionMapper.selectList(new LambdaQueryWrapper<Permission>().in(Permission::getId, permissionIdList));
        Map<Long, Permission> permissionMap = permissionList.stream().collect(Collectors.toMap(Permission::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            RolePermissionLinkVo vo = new RolePermissionLinkVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRole(roleMap.getOrDefault(item.getRoleId(), Role.builder().name("已删除").build()));
            vo.setPermission(permissionMap.getOrDefault(item.getPermissionId(), Permission.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RolePermissionLinkVo> getPage(RolePermissionLinkDto dto) {
        Page<RolePermissionLink> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 角色
        List<Long> roleIdList = info.getRecords().stream().map(RolePermissionLink::getRoleId).toList();
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, roleIdList));
        Map<Long, Role> roleMap = roleList.stream().collect(Collectors.toMap(Role::getId, item -> item));
        // 权限
        List<Long> permissionIdList = info.getRecords().stream().map(RolePermissionLink::getPermissionId).toList();
        List<Permission> permissionList = permissionMapper.selectList(new LambdaQueryWrapper<Permission>().in(Permission::getId, permissionIdList));
        Map<Long, Permission> permissionMap = permissionList.stream().collect(Collectors.toMap(Permission::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            RolePermissionLinkVo vo = new RolePermissionLinkVo();
            BeanUtils.copyProperties(item, vo);
            vo.setRole(roleMap.getOrDefault(item.getRoleId(), Role.builder().name("已删除").build()));
            vo.setPermission(permissionMap.getOrDefault(item.getPermissionId(), Permission.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public RolePermissionLinkVo getOne(RolePermissionLinkDto dto) {
        RolePermissionLink one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 角色
        Role role = Optional.ofNullable(roleMapper.selectById(one.getRoleId())).orElse(Role.builder().name("已删除").build());
        // 权限
        Permission permission = Optional.ofNullable(permissionMapper.selectById(one.getPermissionId())).orElse(Permission.builder().name("已删除").build());
        // 组装VO
        RolePermissionLinkVo vo = new RolePermissionLinkVo();
        BeanUtils.copyProperties(one, vo);
        vo.setRole(role);
        vo.setPermission(permission);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 角色、权限关系
     * @return 结果
     */
    private LambdaQueryChainWrapper<RolePermissionLink> getWrapper(RolePermissionLink entity) {
        LambdaQueryChainWrapper<RolePermissionLink> wrapper = lambdaQuery()
                .eq(entity.getId() != null, RolePermissionLink::getId, entity.getId())
                .eq(entity.getRoleId() != null, RolePermissionLink::getRoleId, entity.getRoleId())
                .eq(entity.getPermissionId() != null, RolePermissionLink::getPermissionId, entity.getPermissionId());
        if (entity instanceof RolePermissionLinkDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    RolePermissionLink::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
