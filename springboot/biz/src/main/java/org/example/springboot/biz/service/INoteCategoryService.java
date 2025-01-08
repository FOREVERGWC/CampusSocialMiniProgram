package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.NoteCategoryDto;
import org.example.springboot.biz.domain.entity.NoteCategory;
import org.example.springboot.biz.domain.vo.NoteCategoryVo;

import java.util.List;

/**
 * <p>
 * 笔记类别服务类
 * </p>
 */
public interface INoteCategoryService extends IService<NoteCategory> {
    /**
     * 查询笔记类别列表
     *
     * @param dto 笔记类别
     * @return 结果
     */
    List<NoteCategoryVo> getList(NoteCategoryDto dto);

    /**
     * 查询笔记类别分页
     *
     * @param dto 笔记类别
     * @return 结果
     */
    IPage<NoteCategoryVo> getPage(NoteCategoryDto dto);

    /**
     * 查询笔记类别
     *
     * @param dto 笔记类别
     * @return 结果
     */
    NoteCategoryVo getOne(NoteCategoryDto dto);

    /**
     * 导出笔记类别
     *
     * @param entity   笔记类别
     * @param response 响应对象
     */
    void exportExcel(NoteCategory entity, HttpServletResponse response);
}
