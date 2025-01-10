package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.CountDislikeDto;
import org.example.springboot.system.domain.entity.CountDislike;
import org.example.springboot.system.domain.vo.CountDislikeVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点踩量服务类
 * </p>
 */
public interface ICountDislikeService extends IService<CountDislike> {
    /**
     * 查询点踩量列表
     *
     * @param dto 点踩量
     * @return 结果
     */
    List<CountDislikeVo> getList(CountDislikeDto dto);

    /**
     * 查询点踩量分页
     *
     * @param dto 点踩量
     * @return 结果
     */
    IPage<CountDislikeVo> getPage(CountDislikeDto dto);

    /**
     * 查询点踩量
     *
     * @param dto 点踩量
     * @return 结果
     */
    CountDislikeVo getOne(CountDislikeDto dto);

    /**
     * 导出点踩量
     *
     * @param entity   点踩量
     * @param response 响应对象
     */
    void exportExcel(CountDislike entity, HttpServletResponse response);

    /**
     * 根据业务ID和业务类型查询点踩量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    Long getCountByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询点踩量分组
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType);
}
