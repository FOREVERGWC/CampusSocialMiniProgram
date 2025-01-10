package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.CountViewDto;
import org.example.springboot.system.domain.entity.CountView;
import org.example.springboot.system.domain.vo.CountViewVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 浏览量服务类
 * </p>
 */
public interface ICountViewService extends IService<CountView> {
    /**
     * 查询浏览量列表
     *
     * @param dto 浏览量
     * @return 结果
     */
    List<CountViewVo> getList(CountViewDto dto);

    /**
     * 查询浏览量分页
     *
     * @param dto 浏览量
     * @return 结果
     */
    IPage<CountViewVo> getPage(CountViewDto dto);

    /**
     * 查询浏览量
     *
     * @param dto 浏览量
     * @return 结果
     */
    CountViewVo getOne(CountViewDto dto);

    /**
     * 导出浏览量
     *
     * @param entity   浏览量
     * @param response 响应对象
     */
    void exportExcel(CountView entity, HttpServletResponse response);

    /**
     * 根据业务ID和业务类型查询浏览量
     *
     * @param bizId   业务ID
     * @param bizType 业务类型
     * @return 结果
     */
    Long getCountByBizIdAndBizType(Long bizId, Integer bizType);

    /**
     * 根据业务ID列表和业务类型查询浏览量分组
     *
     * @param bizIds  业务ID列表
     * @param bizType 业务类型
     * @return 结果
     */
    Map<Long, Long> mapCountByBizIdsAndBizType(List<Long> bizIds, Integer bizType);
}
