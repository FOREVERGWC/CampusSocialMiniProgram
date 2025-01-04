package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.NoteDto;
import org.example.springboot.system.domain.entity.Note;
import org.example.springboot.system.domain.vo.NoteVo;

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
}
