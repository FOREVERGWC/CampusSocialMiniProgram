package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.ActivityDto;
import org.example.springboot.biz.domain.entity.Activity;
import org.example.springboot.biz.domain.vo.ActivityVo;

import java.util.List;

/**
 * <p>
 * 活动服务类
 * </p>
 */
public interface IActivityService extends IService<Activity> {
    /**
     * 查询活动列表
     *
     * @param dto 活动
     * @return 结果
     */
    List<ActivityVo> getList(ActivityDto dto);

    /**
     * 查询活动分页
     *
     * @param dto 活动
     * @return 结果
     */
    IPage<ActivityVo> getPage(ActivityDto dto);

    /**
     * 查询活动
     *
     * @param dto 活动
     * @return 结果
     */
    ActivityVo getOne(ActivityDto dto);

    /**
     * 导出活动
     *
     * @param entity   活动
     * @param response 响应对象
     */
    void exportExcel(Activity entity, HttpServletResponse response);
}
