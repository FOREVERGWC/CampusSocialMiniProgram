package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.CountFavoriteDto;
import org.example.springboot.system.domain.entity.CountFavorite;
import org.example.springboot.system.domain.vo.CountFavoriteVo;
import org.example.springboot.system.domain.vo.FavoriteCountVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收藏量服务类
 * </p>
 */
public interface ICountFavoriteService extends IService<CountFavorite> {
    /**
     * 查询收藏量列表
     *
     * @param dto 收藏量
     * @return 结果
     */
    List<CountFavoriteVo> getList(CountFavoriteDto dto);

    /**
     * 查询收藏量分页
     *
     * @param dto 收藏量
     * @return 结果
     */
    IPage<CountFavoriteVo> getPage(CountFavoriteDto dto);

    /**
     * 查询收藏量
     *
     * @param dto 收藏量
     * @return 结果
     */
    CountFavoriteVo getOne(CountFavoriteDto dto);

    /**
     * 导出收藏量
     *
     * @param entity   收藏量
     * @param response 响应对象
     */
    void exportExcel(CountFavorite entity, HttpServletResponse response);

    /**
     * 增加收藏量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 收藏量
     */
    Long countPlus(Long bizId, Integer bizType);

    /**
     * 减少收藏量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 收藏量
     */
    Long countMinus(Long bizId, Integer bizType);

    /**
     * 根据业务ID和业务类型查询收藏量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    Long getCountByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID和业务类型查询收藏数量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    FavoriteCountVo getCountVoByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询收藏量分组
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询收藏数量分组
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, FavoriteCountVo> mapCountVoByBizIdsAndBizType(List<Long> bizIds, Integer bizType);
}
