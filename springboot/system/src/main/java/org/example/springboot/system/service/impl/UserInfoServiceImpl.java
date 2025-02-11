package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.springboot.common.common.instance.ObjectMapperInstance;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.Gender;
import org.example.springboot.system.domain.dto.UserInfoDto;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.entity.UserInfo;
import org.example.springboot.system.domain.vo.UserInfoVo;
import org.example.springboot.system.mapper.UserInfoMapper;
import org.example.springboot.system.service.IUserInfoService;
import org.example.springboot.system.service.IUserService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息服务实现类
 * </p>
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService, IBaseService<UserInfo> {
    @Resource
    private IUserService userService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @SneakyThrows
    @Override
    public boolean save(UserInfo entity) {
        ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

        Long userId = UserUtils.getLoginUserId();
        entity.setUserId(userId);
        entity.setName(StrUtil.isBlank(entity.getName()) ? "" : entity.getName());
        entity.setGender(StrUtil.isBlank(entity.getGender()) ? Gender.UNKNOWN.getCode() : entity.getGender());
        entity.setCountry(StrUtil.isBlank(entity.getCountry()) ? "" : entity.getCountry());
        entity.setProvince(StrUtil.isBlank(entity.getProvince()) ? "" : entity.getProvince());
        entity.setCity(StrUtil.isBlank(entity.getCity()) ? "" : entity.getCity());
        entity.setCareer(StrUtil.isBlank(entity.getCareer()) ? "" : entity.getCareer());
        entity.setExtra(StrUtil.isBlank(entity.getExtra()) ? objectMapper.writeValueAsString(new JSONObject()) : entity.getExtra());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(UserInfo entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<UserInfoVo> getList(UserInfoDto dto) {
        List<UserInfo> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(UserInfo::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            UserInfoVo vo = new UserInfoVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<UserInfoVo> getPage(UserInfoDto dto) {
        Page<UserInfo> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(UserInfo::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            UserInfoVo vo = new UserInfoVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public UserInfoVo getOne(UserInfoDto dto) {
        UserInfo one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 组装VO
        UserInfoVo vo = new UserInfoVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        return vo;
    }

    @Override
    public UserInfoVo getMyOne() {
        Long userId = UserUtils.getLoginUserId();
        return getOne(UserInfoDto.builder().userId(userId).build());
    }

    @Override
    public void exportExcel(UserInfo entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, UserInfo.class, threadPoolTaskExecutor);
    }

    @Override
    public List<UserInfo> getPageList(UserInfo entity, IPage<UserInfo> page) {
        IPage<UserInfo> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<UserInfo> getWrapper(UserInfo entity) {
        LambdaQueryChainWrapper<UserInfo> wrapper = lambdaQuery()
                .eq(entity.getId() != null, UserInfo::getId, entity.getId())
                .eq(entity.getUserId() != null, UserInfo::getUserId, entity.getUserId())
                .like(StrUtil.isNotBlank(entity.getName()), UserInfo::getName, entity.getName())
                .eq(entity.getGender() != null, UserInfo::getGender, entity.getGender())
                .like(StrUtil.isNotBlank(entity.getCountry()), UserInfo::getCountry, entity.getCountry())
                .like(StrUtil.isNotBlank(entity.getProvince()), UserInfo::getProvince, entity.getProvince())
                .like(StrUtil.isNotBlank(entity.getCity()), UserInfo::getCity, entity.getCity())
                .like(StrUtil.isNotBlank(entity.getCareer()), UserInfo::getCareer, entity.getCareer())
                .like(StrUtil.isNotBlank(entity.getExtra()), UserInfo::getExtra, entity.getExtra());
        if (entity instanceof UserInfoDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    UserInfo::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
