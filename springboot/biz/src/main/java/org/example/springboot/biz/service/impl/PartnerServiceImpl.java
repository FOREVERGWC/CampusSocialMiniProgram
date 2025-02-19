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
import org.example.springboot.biz.domain.dto.PartnerDto;
import org.example.springboot.biz.domain.entity.Partner;
import org.example.springboot.biz.domain.entity.PartnerSubject;
import org.example.springboot.biz.domain.vo.PartnerVo;
import org.example.springboot.biz.mapper.PartnerMapper;
import org.example.springboot.biz.service.IPartnerService;
import org.example.springboot.biz.service.IPartnerSubjectService;
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
 * 组局服务实现类
 * </p>
 */
@Service
public class PartnerServiceImpl extends ServiceImpl<PartnerMapper, Partner> implements IPartnerService, IBaseService<Partner> {
    @Resource
    private IUserService userService;
    @Resource
    private IPartnerSubjectService partnerSubjectService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(Partner entity) {
        Long userId = UserUtils.getLoginUserId();
        entity.setUserId(userId);
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(Partner entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<PartnerVo> getList(PartnerDto dto) {
        List<Partner> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(Partner::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 主题
        List<Long> subjectIdList = list.stream().map(Partner::getSubjectId).toList();
        List<PartnerSubject> subjectList = partnerSubjectService.listByIds(subjectIdList);
        Map<Long, PartnerSubject> subjectMap = subjectList.stream().collect(Collectors.toMap(PartnerSubject::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            PartnerVo vo = new PartnerVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setSubject(subjectMap.getOrDefault(item.getSubjectId(), PartnerSubject.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<PartnerVo> getPage(PartnerDto dto) {
        Page<Partner> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(Partner::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 主题
        List<Long> subjectIdList = info.getRecords().stream().map(Partner::getSubjectId).toList();
        List<PartnerSubject> subjectList = partnerSubjectService.listByIds(subjectIdList);
        Map<Long, PartnerSubject> subjectMap = subjectList.stream().collect(Collectors.toMap(PartnerSubject::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            PartnerVo vo = new PartnerVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setSubject(subjectMap.getOrDefault(item.getSubjectId(), PartnerSubject.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public PartnerVo getOne(PartnerDto dto) {
        Partner one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 主题
        PartnerSubject subject = Optional.ofNullable(partnerSubjectService.getById(one.getSubjectId())).orElse(PartnerSubject.builder().name("已删除").build());
        // 组装VO
        PartnerVo vo = new PartnerVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        vo.setSubject(subject);
        return vo;
    }

    @Override
    public void exportExcel(Partner entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Partner.class, threadPoolTaskExecutor);
    }

    @Override
    public List<Partner> getPageList(Partner entity, IPage<Partner> page) {
        IPage<Partner> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Partner> getWrapper(Partner entity) {
        LambdaQueryChainWrapper<Partner> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Partner::getId, entity.getId())
                .eq(entity.getUserId() != null, Partner::getUserId, entity.getUserId())
                .like(StrUtil.isNotBlank(entity.getTitle()), Partner::getTitle, entity.getTitle())
                .like(StrUtil.isNotBlank(entity.getContent()), Partner::getContent, entity.getContent())
                .eq(entity.getSubjectId() != null, Partner::getSubjectId, entity.getSubjectId())
                .eq(entity.getNum() != null, Partner::getNum, entity.getNum());
        if (entity instanceof PartnerDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Partner::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
