package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.SchoolDto;
import org.example.springboot.biz.domain.entity.School;
import org.example.springboot.biz.domain.vo.SchoolVo;

import java.util.List;

/**
 * <p>
 * 学校服务类
 * </p>
 */
public interface ISchoolService extends IService<School> {
    /**
     * 查询学校列表
     *
     * @param dto 学校
     * @return 结果
     */
    List<SchoolVo> getList(SchoolDto dto);

    /**
     * 查询学校分页
     *
     * @param dto 学校
     * @return 结果
     */
    IPage<SchoolVo> getPage(SchoolDto dto);

    /**
     * 查询学校
     *
     * @param dto 学校
     * @return 结果
     */
    SchoolVo getOne(SchoolDto dto);

    /**
     * 导出学校
     *
     * @param entity   学校
     * @param response 响应对象
     */
    void exportExcel(School entity, HttpServletResponse response);
}
