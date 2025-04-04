package org.example.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.NoteDto;
import org.example.springboot.biz.domain.entity.Note;
import org.example.springboot.biz.domain.vo.NoteVo;

import java.util.List;
import java.util.Map;

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
     * 查询我的笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    IPage<NoteVo> getMyPage(NoteDto dto);

    /**
     * 查询我的点赞笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    IPage<NoteVo> getMyLikePage(NoteDto dto);

    /**
     * 查询我的收藏笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    IPage<NoteVo> getMyFavoritePage(NoteDto dto);

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

    /**
     * 置顶或取消置顶笔记
     *
     * @param id 文章ID
     */
    void handleTop(Long id);

    /**
     * 允许或禁止笔记评论
     *
     * @param id 文章ID
     */
    void handleComment(Long id);

    /**
     * 根据可见性查询我的笔记数量
     *
     * @return 结果
     */
    Map<String, Long> countMyVisible();
}
