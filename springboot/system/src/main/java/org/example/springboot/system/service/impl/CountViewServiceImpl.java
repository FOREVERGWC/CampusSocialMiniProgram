package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.CountViewDto;
import org.example.springboot.system.domain.entity.CountView;
import org.example.springboot.system.domain.vo.CountViewVo;
import org.example.springboot.system.mapper.CountViewMapper;
import org.example.springboot.system.service.ICountViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 浏览量服务实现类
 * </p>
 */
@Service
public class CountViewServiceImpl extends ServiceImpl<CountViewMapper, CountView> implements ICountViewService {
    @Override
    public List<CountViewVo> getList(CountViewDto dto) {
        List<CountView> countViewList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(countViewList)) {
            return List.of();
        }
        // 组装VO
        return countViewList.stream().map(item -> {
            CountViewVo vo = new CountViewVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountViewVo> getPage(CountViewDto dto) {
        Page<CountView> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            CountViewVo vo = new CountViewVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public CountViewVo getOne(CountViewDto dto) {
        CountView one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        CountViewVo vo = new CountViewVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 浏览量
     * @return 结果
     */
    private LambdaQueryChainWrapper<CountView> getWrapper(CountView entity) {
        LambdaQueryChainWrapper<CountView> wrapper = lambdaQuery()
                .eq(entity.getId() != null, CountView::getId, entity.getId())
                .eq(entity.getBizId() != null, CountView::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountView::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountView::getCount, entity.getCount());
        if (entity instanceof CountViewDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    CountView::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
