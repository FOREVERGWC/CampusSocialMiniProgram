package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.CountRateDto;
import org.example.springboot.system.domain.entity.CountRate;
import org.example.springboot.system.domain.vo.CountRateVo;

import java.util.List;

/**
 * <p>
 * 评分量服务类
 * </p>
 */
public interface ICountRateService extends IService<CountRate> {
    /**
     * 查询评分量列表
     *
     * @param dto 评分量
     * @return 结果
     */
    List<CountRateVo> getList(CountRateDto dto);

    /**
     * 查询评分量分页
     *
     * @param dto 评分量
     * @return 结果
     */
    IPage<CountRateVo> getPage(CountRateDto dto);

    /**
     * 查询评分量
     *
     * @param dto 评分量
     * @return 结果
     */
    CountRateVo getOne(CountRateDto dto);
}
