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
import org.example.springboot.biz.domain.dto.RateDto;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.vo.RateVo;
import org.example.springboot.biz.mapper.RateMapper;
import org.example.springboot.biz.service.IRateService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.BizType;
import org.example.springboot.system.common.enums.DeleteEnum;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.service.IAttachmentService;
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
 * 评分服务实现类
 * </p>
 */
@Service
public class RateServiceImpl extends ServiceImpl<RateMapper, Rate> implements IRateService, IBaseService<Rate> {
    @Resource
    private IUserService userService;
    @Resource
    private IAttachmentService attachmentService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(Rate entity) {
        Long userId = UserUtils.getLoginUserId();
        entity.setUserId(userId);
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(Rate entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<RateVo> getList(RateDto dto) {
        List<Rate> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(Rate::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            RateVo vo = new RateVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<RateVo> getPage(RateDto dto) {
        Page<Rate> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(Rate::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            RateVo vo = new RateVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public RateVo getOne(RateDto dto) {
        Rate one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // ID
        Long id = one.getId();
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 评分附件
        List<Attachment> attachmentList = attachmentService.listByBizIdAndBizType(id, BizType.BIZ_RATE.getCode());
        // 组装VO
        RateVo vo = new RateVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        vo.setAttachmentList(attachmentList);
        return vo;
    }

    @Override
    public void exportExcel(Rate entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Rate.class, threadPoolTaskExecutor);
    }

    @Override
    public List<Rate> getPageList(Rate entity, IPage<Rate> page) {
        IPage<Rate> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Rate> getWrapper(Rate entity) {
        LambdaQueryChainWrapper<Rate> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Rate::getId, entity.getId())
                .eq(entity.getUserId() != null, Rate::getUserId, entity.getUserId())
                .like(StrUtil.isNotBlank(entity.getTitle()), Rate::getTitle, entity.getTitle())
                .like(StrUtil.isNotBlank(entity.getContent()), Rate::getContent, entity.getContent())
                .eq(entity.getDeleted() != null, Rate::getDeleted, entity.getDeleted());
        if (entity instanceof RateDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Rate::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
