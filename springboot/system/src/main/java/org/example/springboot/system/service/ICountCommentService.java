package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.CountCommentDto;
import org.example.springboot.system.domain.entity.CountComment;
import org.example.springboot.system.domain.vo.CountCommentVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 增加评论量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     */
    void countPlus(Long bizId, Integer bizType);

    /**
     * 根据业务ID和业务类型查询评论量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    Long getCountByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询评论量
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType);

}
