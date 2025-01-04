package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.RoleMenuLinkDto;
import org.example.springboot.system.domain.entity.RoleMenuLink;
import org.example.springboot.system.domain.vo.RoleMenuLinkVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色、菜单关系服务类
 * </p>
 */
public interface IRoleMenuLinkService extends IService<RoleMenuLink> {
    /**
     * 根据角色ID、菜单ID列表批量保存角色、菜单关系列表
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void saveBatchByRoleIdAndMenuIds(Long roleId, List<Long> menuIds);

    /**
     * 根据角色ID删除角色、菜单关系
     *
     * @param roleId 角色ID
     */
    void removeByRoleId(Serializable roleId);

    /**
     * 根据角色ID列表批量删除角色、菜单关系
     *
     * @param roleIds 角色ID列表
     */
    void removeByRoleIds(Collection<?> roleIds);

    /**
     * 根据菜单ID删除角色、菜单关系
     *
     * @param menuId 菜单ID
     */
    void removeByMenuId(Serializable menuId);

    /**
     * 根据菜单ID列表批量删除角色、菜单关系
     *
     * @param menuIds 菜单ID列表
     */
    void removeByMenuIds(Collection<?> menuIds);

    /**
     * 根据角色ID查询角色、菜单关系列表
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<RoleMenuLink> listByRoleId(Serializable roleId);

    /**
     * 根据角色ID列表批量查询角色、菜单关系列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    List<RoleMenuLink> listByRoleIds(Collection<?> roleIds);

    /**
     * 根据菜单ID查询角色、菜单关系列表
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    List<RoleMenuLink> listByMenuId(Serializable menuId);

    /**
     * 根据菜单ID列表批量查询角色、菜单关系列表
     *
     * @param menuIds 菜单ID列表
     * @return 结果
     */
    List<RoleMenuLink> listByMenuIds(Collection<?> menuIds);

    /**
     * 根据角色ID查询菜单ID列表
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID列表批量查询菜单ID列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    List<Long> listMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * 根据菜单ID查询角色ID列表
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    List<Long> listRoleIdsByMenuId(Long menuId);

    /**
     * 根据菜单ID列表批量查询角色ID列表
     *
     * @param menuIds 菜单ID列表
     * @return 结果
     */
    List<Long> listRoleIdsByMenuIds(List<Long> menuIds);

    /**
     * 查询角色、菜单关系列表
     *
     * @param dto 角色、菜单关系
     * @return 结果
     */
    List<RoleMenuLinkVo> getList(RoleMenuLinkDto dto);

    /**
     * 查询角色、菜单关系分页
     *
     * @param dto 角色、菜单关系
     * @return 结果
     */
    IPage<RoleMenuLinkVo> getPage(RoleMenuLinkDto dto);

    /**
     * 查询角色、菜单关系
     *
     * @param dto 角色、菜单关系
     * @return 结果
     */
    RoleMenuLinkVo getOne(RoleMenuLinkDto dto);
}
