package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.PermissionDto;
import org.example.springboot.system.domain.entity.Permission;
import org.example.springboot.system.domain.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 * 权限服务类
 * </p>
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 根据用户ID查询权限列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<Permission> listByUserId(Long userId);

    /**
     * 查询权限列表
     *
     * @param dto 权限
     * @return 结果
     */
    List<PermissionVo> getList(PermissionDto dto);

    /**
     * 查询权限树
     *
     * @param dto 权限
     * @return 结果
     */
    List<PermissionVo> getTree(PermissionDto dto);

    /**
     * 查询角色权限树
     *
     * @param roleId 角色ID
     * @return 结果
     */
    List<PermissionVo> getRoleTree(Long roleId);

    /**
     * 查询权限分页
     *
     * @param dto 权限
     * @return 结果
     */
    IPage<PermissionVo> getPage(PermissionDto dto);

    /**
     * 查询权限
     *
     * @param dto 权限
     * @return 结果
     */
    PermissionVo getOne(PermissionDto dto);

    /**
     * 导出权限
     *
     * @param entity   权限
     * @param response 响应对象
     */
    void exportExcel(Permission entity, HttpServletResponse response);

    /**
     * 恢复或停用权限
     *
     * @param id 权限ID
     */
    void handleStatus(Long id);
}
