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
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.DeleteEnum;
import org.example.springboot.system.common.enums.EnableStatus;
import org.example.springboot.system.domain.dto.RoleDto;
import org.example.springboot.system.domain.entity.Role;
import org.example.springboot.system.domain.entity.UserRoleLink;
import org.example.springboot.system.domain.vo.RoleVo;
import org.example.springboot.system.mapper.RoleMapper;
import org.example.springboot.system.service.IRoleService;
import org.example.springboot.system.service.IUserRoleLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色服务实现类
 * </p>
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService, IBaseService<Role> {
    @Resource
    private IUserRoleLinkService userRoleLinkService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(Role entity) {
        entity.setStatus(EnableStatus.NORMAL.getCode());
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Role> entityList) {
        entityList.forEach(item -> {
            item.setStatus(EnableStatus.NORMAL.getCode());
            item.setDeleted(DeleteEnum.NORMAL.getCode());
        });
        return super.saveBatch(entityList);
    }

    @Override
    public boolean saveOrUpdate(Role entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Transactional
    @Override
    public boolean removeBatchByIds(Collection<?> list) {
        List<UserRoleLink> linkList = userRoleLinkService.listByRoleIds(list);
        Map<Long, Integer> linkMap = linkList.stream()
                .collect(Collectors.groupingBy(UserRoleLink::getRoleId, Collectors.summingInt(e -> 1)));
        boolean flag = linkMap.values().stream().anyMatch(count -> count > 0);
        if (flag) {
            throw new ServiceException(ResultCode.ROLE_DELETE_ERROR);
        }
        return super.removeBatchByIds(list);
    }

    @Override
    public List<Role> listByUserId(Long userId) {
        List<Long> roleIdList = userRoleLinkService.listRoleIdsByUserId(userId);
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        return Optional.ofNullable(listByIds(roleIdList)).orElse(List.of());
    }

    @Override
    public Map<Long, List<Long>> mapRoleIdsByUserIds(List<Long> userIds) {
        List<UserRoleLink> linkList = userRoleLinkService.listByUserIds(userIds);
        return linkList.stream()
                .collect(Collectors.groupingBy(
                        UserRoleLink::getUserId,
                        Collectors.mapping(UserRoleLink::getRoleId, Collectors.toList())
                ));
    }

    @Override
    public Map<Long, List<Role>> mapByUserIds(List<Long> userIds) {
        List<UserRoleLink> linkList = userRoleLinkService.listByUserIds(userIds);
        List<Long> roleIdList = linkList.stream().map(UserRoleLink::getRoleId).distinct().toList();
        if (CollectionUtil.isEmpty(roleIdList)) {
            return Map.of();
        }
        List<Role> roleList = Optional.ofNullable(listByIds(roleIdList)).orElse(List.of());
        return linkList.stream()
                .collect(Collectors.groupingBy(
                        UserRoleLink::getUserId,
                        Collectors.mapping(
                                link -> roleList.stream()
                                        .filter(role -> Objects.equals(role.getId(), link.getRoleId()))
                                        .findFirst()
                                        .orElse(null),
                                Collectors.filtering(Objects::nonNull, Collectors.toList())
                        )
                ));
    }

    @Override
    public List<RoleVo> getList(RoleDto dto) {
        List<Role> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户数量
        List<Long> roleIdList = list.stream().map(Role::getId).toList();
        Map<Long, Long> countMap = userRoleLinkService.countByRoleIds(roleIdList);
        // 组装VO
        return list.stream().map(item -> {
            RoleVo vo = new RoleVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCount(countMap.getOrDefault(item.getId(), 0L));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RoleVo> getPage(RoleDto dto) {
        Page<Role> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户数量
        List<Long> roleIdList = info.getRecords().stream().map(Role::getId).toList();
        Map<Long, Long> countMap = userRoleLinkService.countByRoleIds(roleIdList);
        // 组装VO
        return info.convert(item -> {
            RoleVo vo = new RoleVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCount(countMap.getOrDefault(item.getId(), 0L));
            return vo;
        });
    }

    @Override
    public RoleVo getOne(RoleDto dto) {
        Role one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户数量
        Long count = userRoleLinkService.countByRoleId(one.getId());
        // 组装VO
        RoleVo vo = new RoleVo();
        BeanUtils.copyProperties(one, vo);
        vo.setCount(count);
        return vo;
    }

    @Override
    public void exportExcel(Role entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Role.class, threadPoolTaskExecutor);
    }

    @Override
    public void handleStatus(Long id) {
        Role role = getById(id);
        if (role == null) {
            throw new ServiceException(ResultCode.ROLE_NOT_FOUND_ERROR);
        }
        if (Objects.equals(EnableStatus.NORMAL.getCode(), role.getStatus())) {
            role.setStatus(EnableStatus.DISABLE.getCode());
        } else {
            role.setStatus(EnableStatus.NORMAL.getCode());
        }
        updateById(role);
    }

    @Override
    public List<Role> getPageList(Role entity, IPage<Role> page) {
        IPage<Role> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Role> getWrapper(Role entity) {
        LambdaQueryChainWrapper<Role> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Role::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getName()), Role::getName, entity.getName())
                .eq(entity.getSort() != null, Role::getSort, entity.getSort())
                .like(StrUtil.isNotBlank(entity.getStatus()), Role::getStatus, entity.getStatus())
                .eq(entity.getDeleted() != null, Role::getDeleted, entity.getDeleted())
                .orderByAsc(Role::getSort);
        if (entity instanceof RoleDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Role::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
