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
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.dto.UserAuthDto;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.entity.UserAuth;
import org.example.springboot.system.domain.vo.UserAuthVo;
import org.example.springboot.system.mapper.UserAuthMapper;
import org.example.springboot.system.service.IUserAuthService;
import org.example.springboot.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户三方授权服务实现类
 * </p>
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService, IBaseService<UserAuth> {
    @Resource
    private IUserService userService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<UserAuthVo> getList(UserAuthDto dto) {
        List<UserAuth> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(UserAuth::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            UserAuthVo vo = new UserAuthVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<UserAuthVo> getPage(UserAuthDto dto) {
        Page<UserAuth> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(UserAuth::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            UserAuthVo vo = new UserAuthVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public UserAuthVo getOne(UserAuthDto dto) {
        UserAuth one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 组装VO
        UserAuthVo vo = new UserAuthVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        return vo;
    }

    @Override
    public void exportExcel(UserAuth entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, UserAuth.class, threadPoolTaskExecutor);
    }

    @Override
    public UserAuth getByOpenId(String openId) {
        return lambdaQuery()
                .eq(UserAuth::getOpenId, openId)
                .one();
    }

    @Override
    public void removeByUserId(Long userId) {
        lambdaUpdate()
                .eq(UserAuth::getUserId, userId)
                .remove();
    }

    @Override
    public UserAuth getByAuthTypeAndOpenIdAnd(Integer authType, String openId) {
        return lambdaQuery()
                .eq(UserAuth::getAuthType, authType)
                .eq(UserAuth::getOpenId, openId)
                .one();
    }

    @Override
    public List<UserAuth> getPageList(UserAuth entity, IPage<UserAuth> page) {
        IPage<UserAuth> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<UserAuth> getWrapper(UserAuth entity) {
        LambdaQueryChainWrapper<UserAuth> wrapper = lambdaQuery()
                .eq(entity.getId() != null, UserAuth::getId, entity.getId())
                .eq(entity.getUserId() != null, UserAuth::getUserId, entity.getUserId())
                .eq(entity.getAuthType() != null, UserAuth::getAuthType, entity.getAuthType())
                .like(StrUtil.isNotBlank(entity.getOpenId()), UserAuth::getOpenId, entity.getOpenId())
                .like(StrUtil.isNotBlank(entity.getAccessToken()), UserAuth::getAccessToken, entity.getAccessToken());
        if (entity instanceof UserAuthDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    UserAuth::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
