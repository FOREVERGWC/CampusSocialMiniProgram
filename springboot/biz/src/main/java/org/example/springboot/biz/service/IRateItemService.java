package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateItemDto;
import org.example.springboot.biz.domain.entity.RateItem;
import org.example.springboot.biz.domain.vo.RateItemVo;

import java.util.List;

/**
 * <p>
 * 评分项服务类
 * </p>
 */
public interface IRateItemService extends IService<RateItem> {
    /**
     * 查询评分项列表
     *
     * @param dto 评分项
     * @return 结果
     */
    List<RateItemVo> getList(RateItemDto dto);

    /**
     * 查询评分项分页
     *
     * @param dto 评分项
     * @return 结果
     */
    IPage<RateItemVo> getPage(RateItemDto dto);

    /**
     * 查询评分项
     *
     * @param dto 评分项
     * @return 结果
     */
    RateItemVo getOne(RateItemDto dto);

    /**
     * 导出评分项
     *
     * @param entity   评分项
     * @param response 响应对象
     */
    void exportExcel(RateItem entity, HttpServletResponse response);
}
