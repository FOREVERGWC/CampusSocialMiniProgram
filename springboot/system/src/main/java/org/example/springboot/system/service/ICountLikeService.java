package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.CountLikeDto;
import org.example.springboot.system.domain.entity.CountLike;
import org.example.springboot.system.domain.vo.CountLikeVo;
import org.example.springboot.system.domain.vo.LikeCountVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点赞量服务类
 * </p>
 */
public interface ICountLikeService extends IService<CountLike> {
    /**
     * 查询点赞量列表
     *
     * @param dto 点赞量
     * @return 结果
     */
    List<CountLikeVo> getList(CountLikeDto dto);

    /**
     * 查询点赞量分页
     *
     * @param dto 点赞量
     * @return 结果
     */
    IPage<CountLikeVo> getPage(CountLikeDto dto);

    /**
     * 查询点赞量
     *
     * @param dto 点赞量
     * @return 结果
     */
    CountLikeVo getOne(CountLikeDto dto);

    /**
     * 导出点赞量
     *
     * @param entity   点赞量
     * @param response 响应对象
     */
    void exportExcel(CountLike entity, HttpServletResponse response);

    /**
     * 增加点赞量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 点赞量
     */
    Long countPlus(Long bizId, Integer bizType);

    /**
     * 减少点赞量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 点赞量
     */
    Long countMinus(Long bizId, Integer bizType);

    /**
     * 根据业务ID和业务类型查询点赞量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    Long getCountByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID和业务类型查询是否已点赞
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    Boolean getHasLikeByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID和业务类型查询点赞数量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    LikeCountVo getCountVoByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询点赞量分组
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询点赞数量分组
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, LikeCountVo> mapCountVoByBizIdsAndBizType(List<Long> bizIds, Integer bizType);
}
