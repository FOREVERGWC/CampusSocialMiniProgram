package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.RolePermissionLinkDto;
import org.example.springboot.system.domain.entity.RolePermissionLink;
import org.example.springboot.system.domain.vo.RolePermissionLinkVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色、权限关系服务类
 * </p>
 */
public interface IRolePermissionLinkService extends IService<RolePermissionLink> {
    /**
     * 根据角色ID、权限ID列表批量保存角色、权限关系列表
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     */
    void saveBatchByRoleIdAndPermissionIds(Long roleId, List<Long> permissionIds);

    /**
     * 根据角色ID删除角色、权限关系
     *
     * @param roleId 角色ID
     */
    void removeByRoleId(Serializable roleId);

    /**
     * 根据角色ID列表批量删除角色、权限关系
     *
     * @param roleIds 角色ID列表
     */
    void removeByRoleIds(Collection<?> roleIds);

    /**
     * 根据权限ID删除角色、权限关系
     *
     * @param permissionId 权限ID
     */
    void removeByPermissionId(Serializable permissionId);

    /**
     * 根据权限ID列表批量删除角色、权限关系
     *
     * @param permissionIds 权限ID列表
     */
    void removeByPermissionIds(Collection<?> permissionIds);

    /**
     * 根据角色ID查询角色、权限关系列表
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<RolePermissionLink> listByRoleId(Serializable roleId);

    /**
     * 根据角色ID列表批量查询角色、权限关系列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    List<RolePermissionLink> listByRoleIds(Collection<?> roleIds);

    /**
     * 根据权限ID查询角色、权限关系列表
     *
     * @param permissionId 权限ID
     * @return 结果
     */
    List<RolePermissionLink> listByPermissionId(Serializable permissionId);

    /**
     * 根据权限ID列表批量查询角色、权限关系列表
     *
     * @param permissionIds 权限ID列表
     * @return 结果
     */
    List<RolePermissionLink> listByPermissionIds(Collection<?> permissionIds);

    /**
     * 根据角色ID查询权限ID列表
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<Long> listPermissionIdsByRoleId(Long roleId);

    /**
     * 根据角色ID列表批量查询权限ID列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    List<Long> listPermissionIdsByRoleIds(List<Long> roleIds);

    /**
     * 根据权限ID查询角色ID列表
     *
     * @param permissionId 权限ID
     * @return 结果
     */
    List<Long> listRoleIdsByPermissionId(Long permissionId);

    /**
     * 根据权限ID列表批量查询角色ID列表
     *
     * @param permissionIds 权限ID列表
     * @return 结果
     */
    List<Long> listRoleIdsByPermissionIds(List<Long> permissionIds);

    /**
     * 查询角色、权限关系列表
     *
     * @param dto 角色、权限关系
     * @return 结果
     */
    List<RolePermissionLinkVo> getList(RolePermissionLinkDto dto);

    /**
     * 查询角色、权限关系分页
     *
     * @param dto 角色、权限关系
     * @return 结果
     */
    IPage<RolePermissionLinkVo> getPage(RolePermissionLinkDto dto);

    /**
     * 查询角色、权限关系
     *
     * @param dto 角色、权限关系
     * @return 结果
     */
    RolePermissionLinkVo getOne(RolePermissionLinkDto dto);
}
