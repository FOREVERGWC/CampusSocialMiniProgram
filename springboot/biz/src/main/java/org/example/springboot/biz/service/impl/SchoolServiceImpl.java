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
import org.example.springboot.biz.domain.dto.SchoolDto;
import org.example.springboot.biz.domain.entity.School;
import org.example.springboot.biz.domain.vo.SchoolVo;
import org.example.springboot.biz.mapper.SchoolMapper;
import org.example.springboot.biz.service.ISchoolService;
import org.example.springboot.biz.service.IUserSchoolService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学校服务实现类
 * </p>
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService, IBaseService<School> {
    @Resource
    private IUserSchoolService userSchoolService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<SchoolVo> getList(SchoolDto dto) {
        List<School> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 组装VO
        return list.stream().map(item -> {
            SchoolVo vo = new SchoolVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<SchoolVo> getPage(SchoolDto dto) {
        Page<School> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            SchoolVo vo = new SchoolVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public SchoolVo getOne(SchoolDto dto) {
        School one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        SchoolVo vo = new SchoolVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void exportExcel(School entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, School.class, threadPoolTaskExecutor);
    }

    @Override
    public List<School> getPageList(School entity, IPage<School> page) {
        IPage<School> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<School> getWrapper(School entity) {
        LambdaQueryChainWrapper<School> wrapper = lambdaQuery()
                .eq(entity.getId() != null, School::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getName()), School::getName, entity.getName());
        if (entity instanceof SchoolDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    School::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
