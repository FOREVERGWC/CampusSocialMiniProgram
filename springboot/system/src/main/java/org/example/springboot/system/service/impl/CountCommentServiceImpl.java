package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.springboot.system.domain.dto.CountCommentDto;
import org.example.springboot.system.domain.entity.CountComment;
import org.example.springboot.system.domain.vo.CountCommentVo;
import org.example.springboot.system.mapper.CountCommentMapper;
import org.example.springboot.system.service.ICountCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论量服务实现类
 * </p>
 */
@Service
public class CountCommentServiceImpl extends ServiceImpl<CountCommentMapper, CountComment> implements ICountCommentService {
    @Override
    public List<CountCommentVo> getList(CountCommentDto dto) {
        List<CountComment> countCommentList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(countCommentList)) {
            return List.of();
        }
        // 组装VO
        return countCommentList.stream().map(item -> {
            CountCommentVo vo = new CountCommentVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public IPage<CountCommentVo> getPage(CountCommentDto dto) {
        Page<CountComment> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        return info.convert(item -> {
            CountCommentVo vo = new CountCommentVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public CountCommentVo getOne(CountCommentDto dto) {
        CountComment one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 组装VO
        CountCommentVo vo = new CountCommentVo();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 评论量
     * @return 结果
     */
    private LambdaQueryChainWrapper<CountComment> getWrapper(CountComment entity) {
        LambdaQueryChainWrapper<CountComment> wrapper = lambdaQuery()
                .eq(entity.getId() != null, CountComment::getId, entity.getId())
                .eq(entity.getBizId() != null, CountComment::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, CountComment::getBizType, entity.getBizType())
                .eq(entity.getCount() != null, CountComment::getCount, entity.getCount());
        if (entity instanceof CountCommentDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    CountComment::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
