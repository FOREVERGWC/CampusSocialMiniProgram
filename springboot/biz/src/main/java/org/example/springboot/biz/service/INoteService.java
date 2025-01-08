package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.NoteDto;
import org.example.springboot.biz.domain.entity.Note;
import org.example.springboot.biz.domain.vo.NoteVo;

import java.util.List;

/**
 * <p>
 * 笔记服务类
 * </p>
 */
public interface INoteService extends IService<Note> {
    /**
     * 查询笔记列表
     *
     * @param dto 笔记
     * @return 结果
     */
    List<NoteVo> getList(NoteDto dto);

    /**
     * 查询笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    IPage<NoteVo> getPage(NoteDto dto);

    /**
     * 查询笔记
     *
     * @param dto 笔记
     * @return 结果
     */
    NoteVo getOne(NoteDto dto);

    /**
     * 导出笔记
     *
     * @param entity   笔记
     * @param response 响应对象
     */
    void exportExcel(Note entity, HttpServletResponse response);
}
