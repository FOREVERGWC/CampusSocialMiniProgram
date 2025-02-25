package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.ActivityCategoryDto;
import org.example.springboot.biz.domain.entity.ActivityCategory;
import org.example.springboot.biz.domain.vo.ActivityCategoryVo;

import java.util.List;

/**
 * <p>
 * 活动类别服务类
 * </p>
 */
public interface IActivityCategoryService extends IService<ActivityCategory> {
    /**
     * 查询活动类别列表
     *
     * @param dto 活动类别
     * @return 结果
     */
    List<ActivityCategoryVo> getList(ActivityCategoryDto dto);

    /**
     * 查询活动类别分页
     *
     * @param dto 活动类别
     * @return 结果
     */
    IPage<ActivityCategoryVo> getPage(ActivityCategoryDto dto);

    /**
     * 查询活动类别
     *
     * @param dto 活动类别
     * @return 结果
     */
    ActivityCategoryVo getOne(ActivityCategoryDto dto);

    /**
     * 导出活动类别
     *
     * @param entity   活动类别
     * @param response 响应对象
     */
    void exportExcel(ActivityCategory entity, HttpServletResponse response);
}
