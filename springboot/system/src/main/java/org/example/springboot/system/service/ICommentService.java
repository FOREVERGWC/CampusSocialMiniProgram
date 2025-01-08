package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.CommentDto;
import org.example.springboot.system.domain.entity.Comment;
import org.example.springboot.system.domain.vo.CommentVo;

import java.util.List;

/**
 * <p>
 * 评论服务类
 * </p>
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 查询评论列表
     *
     * @param dto 评论
     * @return 结果
     */
    List<CommentVo> getList(CommentDto dto);

    /**
     * 查询评论分页
     *
     * @param dto 评论
     * @return 结果
     */
    IPage<CommentVo> getPage(CommentDto dto);

    /**
     * 查询评论
     *
     * @param dto 评论
     * @return 结果
     */
    CommentVo getOne(CommentDto dto);

    /**
     * 导出评论
     *
     * @param entity   评论
     * @param response 响应对象
     */
    void exportExcel(Comment entity, HttpServletResponse response);
}
