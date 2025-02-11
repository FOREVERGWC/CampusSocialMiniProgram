package org.example.springboot.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.UserSchoolDto;
import org.example.springboot.biz.domain.entity.School;
import org.example.springboot.biz.domain.entity.UserSchool;
import org.example.springboot.biz.domain.vo.UserSchoolVo;
import org.example.springboot.biz.mapper.SchoolMapper;
import org.example.springboot.biz.mapper.UserSchoolMapper;
import org.example.springboot.biz.service.IUserSchoolService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.domain.entity.User;
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
 * 用户学校服务实现类
 * </p>
 */
@Service
public class UserSchoolServiceImpl extends ServiceImpl<UserSchoolMapper, UserSchool> implements IUserSchoolService, IBaseService<UserSchool> {
    @Resource
    private IUserService userService;
    @Resource
    private SchoolMapper schoolMapper;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(UserSchool entity) {
        Long userId = UserUtils.getLoginUserId();
        entity.setUserId(userId);
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(UserSchool entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<UserSchoolVo> getList(UserSchoolDto dto) {
        List<UserSchool> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(UserSchool::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 学校
        List<Long> schoolIdList = list.stream().map(UserSchool::getSchoolId).toList();
        List<School> schoolList = schoolMapper.selectBatchIds(schoolIdList);
        Map<Long, School> schoolMap = schoolList.stream().collect(Collectors.toMap(School::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            UserSchoolVo vo = new UserSchoolVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setSchool(schoolMap.getOrDefault(item.getSchoolId(), School.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<UserSchoolVo> getPage(UserSchoolDto dto) {
        Page<UserSchool> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(UserSchool::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 学校
        List<Long> schoolIdList = info.getRecords().stream().map(UserSchool::getSchoolId).toList();
        List<School> schoolList = schoolMapper.selectBatchIds(schoolIdList);
        Map<Long, School> schoolMap = schoolList.stream().collect(Collectors.toMap(School::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            UserSchoolVo vo = new UserSchoolVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setSchool(schoolMap.getOrDefault(item.getSchoolId(), School.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public UserSchoolVo getOne(UserSchoolDto dto) {
        UserSchool one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 学校
        School school = Optional.ofNullable(schoolMapper.selectById(one.getSchoolId())).orElse(School.builder().name("已删除").build());
        // 组装VO
        UserSchoolVo vo = new UserSchoolVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        vo.setSchool(school);
        return vo;
    }

    @Override
    public UserSchoolVo getMyOne() {
        Long userId = UserUtils.getLoginUserId();
        return getOne(UserSchoolDto.builder().userId(userId).build());
    }

    @Override
    public void exportExcel(UserSchool entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, UserSchool.class, threadPoolTaskExecutor);
    }

    @Override
    public List<UserSchool> getPageList(UserSchool entity, IPage<UserSchool> page) {
        IPage<UserSchool> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<UserSchool> getWrapper(UserSchool entity) {
        LambdaQueryChainWrapper<UserSchool> wrapper = lambdaQuery()
                .eq(entity.getId() != null, UserSchool::getId, entity.getId())
                .eq(entity.getUserId() != null, UserSchool::getUserId, entity.getUserId())
                .eq(entity.getSchoolId() != null, UserSchool::getSchoolId, entity.getSchoolId())
                .eq(StrUtil.isNotBlank(entity.getStudentId()), UserSchool::getStudentId, entity.getStudentId());
        if (entity instanceof UserSchoolDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    UserSchool::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
