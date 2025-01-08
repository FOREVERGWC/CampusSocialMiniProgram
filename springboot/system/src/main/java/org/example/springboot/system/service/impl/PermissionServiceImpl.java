package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.DataUtils;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.EnableStatus;
import org.example.springboot.system.domain.dto.PermissionDto;
import org.example.springboot.system.domain.entity.Permission;
import org.example.springboot.system.domain.vo.PermissionVo;
import org.example.springboot.system.mapper.PermissionMapper;
import org.example.springboot.system.service.IPermissionService;
import org.example.springboot.system.service.IRolePermissionLinkService;
import org.example.springboot.system.service.IUserRoleLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 权限服务实现类
 * </p>
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService, IBaseService<Permission> {
    @Resource
    private IRolePermissionLinkService rolePermissionLinkService;
    @Resource
    private IUserRoleLinkService userRoleLinkService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(Permission entity) {
        entity.setStatus(EnableStatus.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(Permission entity) {
        // 祖级权限ID
        if (entity.getParentId() == 0L) {
            entity.setAncestorId(0L);
        } else {
            Long ancestorId = DataUtils.getAncestorId(entity.getParentId(), this::getById, Permission::getParentId);
            entity.setAncestorId(ancestorId);
        }
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<Permission> listByUserId(Long userId) {
        List<Long> roleIdList = userRoleLinkService.listRoleIdsByUserId(userId);
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        List<Long> permissionIdList = rolePermissionLinkService.listPermissionIdsByRoleIds(roleIdList);
        if (CollectionUtil.isEmpty(permissionIdList)) {
            return List.of();
        }
        return Optional.ofNullable(listByIds(permissionIdList)).orElse(List.of());
    }

    @Override
    public List<PermissionVo> getList(PermissionDto dto) {
        List<Permission> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            PermissionVo vo = new PermissionVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public List<PermissionVo> getTree(PermissionDto dto) {
        List<PermissionVo> vos = getList(dto);
        // 树
        return DataUtils.listToTree(vos, PermissionVo::getParentId, PermissionVo::setChildren, PermissionVo::getId, 0L, null, null);
    }

    @Override
    public List<PermissionVo> getRoleTree(Long roleId) {
        List<Long> permissionIdList = rolePermissionLinkService.listPermissionIdsByRoleId(roleId);
        if (CollectionUtil.isEmpty(permissionIdList)) {
            return List.of();
        }
        List<Permission> permissionList = listByIds(permissionIdList);
        if (CollectionUtil.isEmpty(permissionIdList)) {
            return List.of();
        }
        List<PermissionVo> vos = permissionList.stream().map(item -> {
            PermissionVo vo = new PermissionVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
        // 树
        return DataUtils.listToTree(vos, PermissionVo::getParentId, PermissionVo::setChildren, PermissionVo::getId, 0L, null, null);
    }

    @Override
    public IPage<PermissionVo> getPage(PermissionDto dto) {
        // 祖级
        dto.setAncestorId(0L);
        Page<Permission> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        Page<Permission> info = getWrapper(dto)
                .select(Permission::getId)
                .page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 子级
        List<Long> idList = info.getRecords().stream().map(Permission::getId).toList();
        List<Permission> permissionList = lambdaQuery()
                .in(Permission::getId, idList)
                .or()
                .in(Permission::getAncestorId, idList)
                .list();
        // 组装VO
        List<PermissionVo> vos = permissionList.stream().map(item -> {
            PermissionVo vo = new PermissionVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
        // 树
        List<PermissionVo> tree = DataUtils.listToTree(
                vos,
                PermissionVo::getParentId,
                PermissionVo::setChildren,
                PermissionVo::getId,
                0L,
                PermissionVo::getSort,
                Comparator.naturalOrder()
        );
        // 组装VO
        IPage<PermissionVo> convert = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        convert.setRecords(tree);
        return convert;
    }

    @Override
    public PermissionVo getOne(PermissionDto dto) {
        Permission one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        PermissionVo vo = new PermissionVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(Permission entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Permission.class, threadPoolTaskExecutor);
    }

    @Override
    public void handleStatus(Long id) {
        Permission permission = getById(id);
        if (permission == null) {
            throw new ServiceException(ResultCode.PERMISSION_NOT_FOUND_ERROR);
        }
        if (Objects.equals(EnableStatus.NORMAL.getCode(), permission.getStatus())) {
            permission.setStatus(EnableStatus.DISABLE.getCode());
        } else {
            permission.setStatus(EnableStatus.NORMAL.getCode());
        }
        updateById(permission);
    }

    @Override
    public List<Permission> getPageList(Permission entity, IPage<Permission> page) {
        IPage<Permission> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Permission> getWrapper(Permission entity) {
        LambdaQueryChainWrapper<Permission> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Permission::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getName()), Permission::getName, entity.getName())
                .like(StrUtil.isNotBlank(entity.getCode()), Permission::getCode, entity.getCode())
                .eq(entity.getParentId() != null, Permission::getParentId, entity.getParentId())
                .eq(entity.getAncestorId() != null, Permission::getAncestorId, entity.getAncestorId())
                .eq(entity.getSort() != null, Permission::getSort, entity.getSort())
                .eq(entity.getStatus() != null, Permission::getStatus, entity.getStatus())
                .orderByAsc(Permission::getSort);
        if (entity instanceof PermissionDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Permission::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
