package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.UserRoleLinkDto;
import org.example.springboot.system.domain.entity.UserRoleLink;
import org.example.springboot.system.domain.vo.UserRoleLinkVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户、角色关系服务类
 * </p>
 */
public interface IUserRoleLinkService extends IService<UserRoleLink> {
    /**
     * 根据用户ID、角色ID列表批量保存用户、角色关系列表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void saveBatchByUserIdAndRoleIds(Long userId, List<Long> roleIds);

    /**
     * 根据用户ID删除用户、角色关系
     *
     * @param userId 用户ID
     */
    void removeByUserId(Serializable userId);

    /**
     * 根据用户ID列表批量删除用户、角色关系
     *
     * @param userIds 用户ID列表
     */
    void removeByUserIds(Collection<?> userIds);

    /**
     * 根据角色ID删除用户、角色关系
     *
     * @param roleId 角色ID
     */
    void removeByRoleId(Serializable roleId);

    /**
     * 根据角色ID列表批量删除用户、角色关系
     *
     * @param roleIds 角色ID列表
     */
    void removeByRoleIds(Collection<?> roleIds);

    /**
     * 根据用户ID查询用户、角色关系列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<UserRoleLink> listByUserId(Serializable userId);

    /**
     * 根据用户ID列表批量查询用户、角色关系列表
     *
     * @param userIds 用户ID列表
     * @return 结果
     */
    List<UserRoleLink> listByUserIds(Collection<?> userIds);

    /**
     * 根据角色ID查询用户、角色关系列表
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<UserRoleLink> listByRoleId(Serializable roleId);

    /**
     * 根据角色ID列表批量查询用户、角色关系列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    List<UserRoleLink> listByRoleIds(Collection<?> roleIds);

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<Long> listRoleIdsByUserId(Long userId);

    /**
     * 根据用户ID列表批量查询角色ID列表
     *
     * @param userIds 用户ID列表
     * @return 结果
     */
    List<Long> listRoleIdsByUserIds(List<Long> userIds);

    /**
     * 根据角色ID查询用户ID列表
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<Long> listUserIdsByRoleId(Long roleId);

    /**
     * 根据角色ID列表批量查询用户ID列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    List<Long> listUserIdsByRoleIds(List<Long> roleIds);

    /**
     * 根据用户ID计数角色ID数量
     *
     * @param userId 用户ID
     * @return 结果
     */
    Long countByUserId(Long userId);

    /**
     * 根据用户ID列表批量计数角色ID数量列表
     *
     * @param userIds 用户ID列表
     * @return 结果
     */
    Map<Long, Long> countByUserIds(List<Long> userIds);

    /**
     * 根据角色ID计数用户ID数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    Long countByRoleId(Long roleId);

    /**
     * 根据角色ID列表批量计数用户ID数量列表
     *
     * @param roleIds 角色ID列表
     * @return 结果
     */
    Map<Long, Long> countByRoleIds(List<Long> roleIds);

    /**
     * 查询用户、角色关系列表
     *
     * @param dto 用户、角色关系
     * @return 结果
     */
    List<UserRoleLinkVo> getList(UserRoleLinkDto dto);

    /**
     * 查询用户、角色关系分页
     *
     * @param dto 用户、角色关系
     * @return 结果
     */
    IPage<UserRoleLinkVo> getPage(UserRoleLinkDto dto);

    /**
     * 查询用户、角色关系
     *
     * @param dto 用户、角色关系
     * @return 结果
     */
    UserRoleLinkVo getOne(UserRoleLinkDto dto);
}
