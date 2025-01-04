package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.RateDto;
import org.example.springboot.system.domain.entity.Rate;
import org.example.springboot.system.domain.vo.RateVo;

import java.util.List;

/**
 * <p>
 * 评分服务类
 * </p>
 */
public interface IRateService extends IService<Rate> {
    /**
     * 查询评分列表
     *
     * @param dto 评分
     * @return 结果
     */
    List<RateVo> getList(RateDto dto);

    /**
     * 查询评分分页
     *
     * @param dto 评分
     * @return 结果
     */
    IPage<RateVo> getPage(RateDto dto);

    /**
     * 查询评分
     *
     * @param dto 评分
     * @return 结果
     */
    RateVo getOne(RateDto dto);
}
