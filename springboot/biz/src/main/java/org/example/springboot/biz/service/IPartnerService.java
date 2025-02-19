package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.PartnerDto;
import org.example.springboot.biz.domain.entity.Partner;
import org.example.springboot.biz.domain.vo.PartnerVo;

import java.util.List;

/**
 * <p>
 * 组局服务类
 * </p>
 */
public interface IPartnerService extends IService<Partner> {
    /**
     * 查询组局列表
     *
     * @param dto 组局
     * @return 结果
     */
    List<PartnerVo> getList(PartnerDto dto);

    /**
     * 查询组局分页
     *
     * @param dto 组局
     * @return 结果
     */
    IPage<PartnerVo> getPage(PartnerDto dto);

    /**
     * 查询组局
     *
     * @param dto 组局
     * @return 结果
     */
    PartnerVo getOne(PartnerDto dto);

    /**
     * 导出组局
     *
     * @param entity   组局
     * @param response 响应对象
     */
    void exportExcel(Partner entity, HttpServletResponse response);
}
