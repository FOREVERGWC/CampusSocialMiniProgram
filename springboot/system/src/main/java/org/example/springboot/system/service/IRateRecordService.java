package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.RateRecordDto;
import org.example.springboot.system.domain.entity.RateRecord;
import org.example.springboot.system.domain.vo.RateRecordVo;

import java.util.List;

/**
 * <p>
 * 评分记录服务类
 * </p>
 */
public interface IRateRecordService extends IService<RateRecord> {
    /**
     * 查询评分记录列表
     *
     * @param dto 评分记录
     * @return 结果
     */
    List<RateRecordVo> getList(RateRecordDto dto);

    /**
     * 查询评分记录分页
     *
     * @param dto 评分记录
     * @return 结果
     */
    IPage<RateRecordVo> getPage(RateRecordDto dto);

    /**
     * 查询评分记录
     *
     * @param dto 评分记录
     * @return 结果
     */
    RateRecordVo getOne(RateRecordDto dto);
}
