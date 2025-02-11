package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.UserSchoolDto;
import org.example.springboot.biz.domain.entity.UserSchool;
import org.example.springboot.biz.domain.vo.UserSchoolVo;

import java.util.List;

/**
 * <p>
 * 用户学校服务类
 * </p>
 */
public interface IUserSchoolService extends IService<UserSchool> {
    /**
     * 查询用户学校列表
     *
     * @param dto 用户学校
     * @return 结果
     */
    List<UserSchoolVo> getList(UserSchoolDto dto);

    /**
     * 查询用户学校分页
     *
     * @param dto 用户学校
     * @return 结果
     */
    IPage<UserSchoolVo> getPage(UserSchoolDto dto);

    /**
     * 查询用户学校
     *
     * @param dto 用户学校
     * @return 结果
     */
    UserSchoolVo getOne(UserSchoolDto dto);

    /**
     * 查询我的学校
     *
     * @return 结果
     */
    UserSchoolVo getMyOne();

    /**
     * 导出用户学校
     *
     * @param entity   用户学校
     * @param response 响应对象
     */
    void exportExcel(UserSchool entity, HttpServletResponse response);
}
