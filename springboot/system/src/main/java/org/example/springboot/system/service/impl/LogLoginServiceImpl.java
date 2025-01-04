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
import org.example.springboot.system.domain.dto.LogLoginDto;
import org.example.springboot.system.domain.entity.LogLogin;
import org.example.springboot.system.domain.vo.LogLoginVo;
import org.example.springboot.system.mapper.LogLoginMapper;
import org.example.springboot.system.service.ILogLoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志服务实现类
 * </p>
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService, IBaseService<LogLogin> {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<LogLoginVo> getList(LogLoginDto dto) {
        List<LogLogin> logLoginList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(logLoginList)) {
            return List.of();
        }
        // 组装VO
        return logLoginList.stream().map(item -> {
            LogLoginVo vo = new LogLoginVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<LogLoginVo> getPage(LogLoginDto dto) {
        Page<LogLogin> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            LogLoginVo vo = new LogLoginVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public LogLoginVo getOne(LogLoginDto dto) {
        LogLogin one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        LogLoginVo vo = new LogLoginVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(LogLogin entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, LogLogin.class, threadPoolTaskExecutor);
    }

    @Override
    public List<LogLogin> getPageList(LogLogin entity, IPage<LogLogin> page) {
        return baseMapper.getPageList((page.getCurrent() - 1) * page.getSize(), page.getSize(), getWrapper(entity));
    }

    @Override
    public LambdaQueryChainWrapper<LogLogin> getWrapper(LogLogin entity) {
        LambdaQueryChainWrapper<LogLogin> wrapper = lambdaQuery()
                .eq(entity.getId() != null, LogLogin::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getOs()), LogLogin::getOs, entity.getOs())
                .like(StrUtil.isNotBlank(entity.getBrowser()), LogLogin::getBrowser, entity.getBrowser())
                .like(StrUtil.isNotBlank(entity.getIp()), LogLogin::getIp, entity.getIp())
                .like(StrUtil.isNotBlank(entity.getLocation()), LogLogin::getLocation, entity.getLocation())
                .eq(entity.getStatus() != null, LogLogin::getStatus, entity.getStatus())
                .like(StrUtil.isNotBlank(entity.getMsg()), LogLogin::getMsg, entity.getMsg())
                .orderByDesc(LogLogin::getCreateTime);
        if (entity instanceof LogLoginDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    LogLogin::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
