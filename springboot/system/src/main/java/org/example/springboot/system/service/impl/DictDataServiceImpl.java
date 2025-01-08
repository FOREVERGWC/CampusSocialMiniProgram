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
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.EnableStatus;
import org.example.springboot.system.domain.dto.DictDataDto;
import org.example.springboot.system.domain.entity.DictData;
import org.example.springboot.system.domain.entity.DictType;
import org.example.springboot.system.domain.vo.DictDataVo;
import org.example.springboot.system.mapper.DictDataMapper;
import org.example.springboot.system.service.IDictDataService;
import org.example.springboot.system.service.IDictTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典数据服务实现类
 * </p>
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService, IBaseService<DictData> {
    @Resource
    private IDictTypeService dictTypeService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(DictData entity) {
        entity.setStatus(EnableStatus.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(DictData entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<DictDataVo> getList(DictDataDto dto) {
        List<DictData> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 类型
        List<Long> typeIdList = list.stream().map(DictData::getTypeId).toList();
        List<DictType> typeList = dictTypeService.listByIds(typeIdList);
        Map<Long, DictType> typeMap = typeList.stream().collect(Collectors.toMap(DictType::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            DictDataVo vo = new DictDataVo();
            BeanUtils.copyProperties(item, vo);
            vo.setType(typeMap.getOrDefault(item.getTypeId(), DictType.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<DictDataVo> getPage(DictDataDto dto) {
        Page<DictData> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 类型
        List<Long> typeIdList = info.getRecords().stream().map(DictData::getTypeId).toList();
        List<DictType> typeList = dictTypeService.listByIds(typeIdList);
        Map<Long, DictType> typeMap = typeList.stream().collect(Collectors.toMap(DictType::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            DictDataVo vo = new DictDataVo();
            BeanUtils.copyProperties(item, vo);
            vo.setType(typeMap.getOrDefault(item.getTypeId(), DictType.builder().name("已删除").build()));
            return vo;
        });
    }

    @Override
    public DictDataVo getOne(DictDataDto dto) {
        DictData one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 类型
        DictType type = Optional.ofNullable(dictTypeService.getById(one.getTypeId())).orElse(DictType.builder().name("已删除").build());
        // 组装VO
        DictDataVo vo = new DictDataVo();
        BeanUtils.copyProperties(one, vo);
        vo.setType(type);
        return vo;
    }

    @Override
    public void exportExcel(DictData entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, DictData.class, threadPoolTaskExecutor);
    }

    @Override
    public void handleStatus(Long id) {
        DictData dictData = getById(id);
        if (dictData == null) {
            throw new ServiceException(ResultCode.DICT_DATA_NOT_FOUND_ERROR);
        }
        if (Objects.equals(EnableStatus.NORMAL.getCode(), dictData.getStatus())) {
            dictData.setStatus(EnableStatus.DISABLE.getCode());
        } else {
            dictData.setStatus(EnableStatus.NORMAL.getCode());
        }
        updateById(dictData);
    }

    @Override
    public List<DictData> getPageList(DictData entity, IPage<DictData> page) {
        IPage<DictData> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<DictData> getWrapper(DictData entity) {
        if (entity instanceof DictDataDto dto) {
            String code = dto.getCode();
            DictType dictType = dictTypeService.getByCode(code);
            if (dictType != null) {
                entity.setTypeId(dictType.getId());
            }
        }
        LambdaQueryChainWrapper<DictData> wrapper = lambdaQuery()
                .eq(entity.getId() != null, DictData::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getLabel()), DictData::getLabel, entity.getLabel())
                .like(StrUtil.isNotBlank(entity.getValue()), DictData::getValue, entity.getValue())
                .eq(entity.getTypeId() != null, DictData::getTypeId, entity.getTypeId())
                .eq(entity.getSort() != null, DictData::getSort, entity.getSort())
                .eq(entity.getStatus() != null, DictData::getStatus, entity.getStatus());
        if (entity instanceof DictDataDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    DictData::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
