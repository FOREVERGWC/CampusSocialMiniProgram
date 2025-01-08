package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.CountFavoriteDto;
import org.example.springboot.system.domain.entity.CountFavorite;
import org.example.springboot.system.domain.vo.CountFavoriteVo;

import java.util.List;

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
}
