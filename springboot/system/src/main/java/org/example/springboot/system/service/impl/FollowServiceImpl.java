package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.dto.FollowDto;
import org.example.springboot.system.domain.entity.Follow;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.FollowVo;
import org.example.springboot.system.mapper.FollowMapper;
import org.example.springboot.system.service.IFollowService;
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
 * 关注服务实现类
 * </p>
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService, IBaseService<Follow> {
    @Resource
    private IUserService userService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<FollowVo> getList(FollowDto dto) {
        List<Follow> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(Follow::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 关注用户
        List<Long> followIdList = list.stream().map(Follow::getFollowId).toList();
        List<User> followList = userService.listByIds(followIdList);
        Map<Long, User> followMap = followList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            FollowVo vo = new FollowVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setFollow(followMap.getOrDefault(item.getFollowId(), User.builder().build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<FollowVo> getPage(FollowDto dto) {
        Page<Follow> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(Follow::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 关注用户
        List<Long> followIdList = info.getRecords().stream().map(Follow::getFollowId).toList();
        List<User> followList = userService.listByIds(followIdList);
        Map<Long, User> followMap = followList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            FollowVo vo = new FollowVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setFollow(followMap.getOrDefault(item.getFollowId(), User.builder().build()));
            return vo;
        });
    }

    @Override
    public FollowVo getOne(FollowDto dto) {
        Follow one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 关注用户
        User follow = Optional.ofNullable(userService.getById(one.getFollowId())).orElse(User.builder().build());
        // 组装VO
        FollowVo vo = new FollowVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        vo.setFollow(follow);
        return vo;
    }

    @Override
    public void exportExcel(Follow entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Follow.class, threadPoolTaskExecutor);
    }

    @Override
    public List<Follow> getPageList(Follow entity, IPage<Follow> page) {
        IPage<Follow> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Follow> getWrapper(Follow entity) {
        LambdaQueryChainWrapper<Follow> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Follow::getId, entity.getId())
                .eq(entity.getUserId() != null, Follow::getUserId, entity.getUserId())
                .eq(entity.getFollowId() != null, Follow::getFollowId, entity.getFollowId())
                .eq(entity.getStatus() != null, Follow::getStatus, entity.getStatus());
        if (entity instanceof FollowDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Follow::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
