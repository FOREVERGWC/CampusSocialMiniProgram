package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.PartnerSubjectDto;
import org.example.springboot.biz.domain.entity.PartnerSubject;
import org.example.springboot.biz.domain.vo.PartnerSubjectVo;

import java.util.List;

/**
 * <p>
 * 组局主题服务类
 * </p>
 */
public interface IPartnerSubjectService extends IService<PartnerSubject> {
    /**
     * 查询组局主题列表
     *
     * @param dto 组局主题
     * @return 结果
     */
    List<PartnerSubjectVo> getList(PartnerSubjectDto dto);

    /**
     * 查询组局主题分页
     *
     * @param dto 组局主题
     * @return 结果
     */
    IPage<PartnerSubjectVo> getPage(PartnerSubjectDto dto);

    /**
     * 查询组局主题
     *
     * @param dto 组局主题
     * @return 结果
     */
    PartnerSubjectVo getOne(PartnerSubjectDto dto);

    /**
     * 导出组局主题
     *
     * @param entity   组局主题
     * @param response 响应对象
     */
    void exportExcel(PartnerSubject entity, HttpServletResponse response);
}
