package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateRecordDto;
import org.example.springboot.biz.domain.entity.RateRecord;
import org.example.springboot.biz.domain.vo.RateRecordVo;

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

    /**
     * 导出评分记录
     *
     * @param entity   评分记录
     * @param response 响应对象
     */
    void exportExcel(RateRecord entity, HttpServletResponse response);
}
