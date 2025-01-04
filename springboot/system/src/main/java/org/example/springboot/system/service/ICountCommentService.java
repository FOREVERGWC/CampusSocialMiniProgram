package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.CountCommentDto;
import org.example.springboot.system.domain.entity.CountComment;
import org.example.springboot.system.domain.vo.CountCommentVo;

import java.util.List;

/**
 * <p>
 * 评论量服务类
 * </p>
 */
public interface ICountCommentService extends IService<CountComment> {
    /**
     * 查询评论量列表
     *
     * @param dto 评论量
     * @return 结果
     */
    List<CountCommentVo> getList(CountCommentDto dto);

    /**
     * 查询评论量分页
     *
     * @param dto 评论量
     * @return 结果
     */
    IPage<CountCommentVo> getPage(CountCommentDto dto);

    /**
     * 查询评论量
     *
     * @param dto 评论量
     * @return 结果
     */
    CountCommentVo getOne(CountCommentDto dto);
}
