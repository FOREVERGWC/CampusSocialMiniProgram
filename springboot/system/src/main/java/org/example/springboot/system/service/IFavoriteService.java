package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.FavoriteDto;
import org.example.springboot.system.domain.entity.Favorite;
import org.example.springboot.system.domain.vo.FavoriteVo;

import java.util.List;

/**
 * <p>
 * 收藏服务类
 * </p>
 */
public interface IFavoriteService extends IService<Favorite> {
    /**
     * 查询收藏列表
     *
     * @param dto 收藏
     * @return 结果
     */
    List<FavoriteVo> getList(FavoriteDto dto);

    /**
     * 查询收藏分页
     *
     * @param dto 收藏
     * @return 结果
     */
    IPage<FavoriteVo> getPage(FavoriteDto dto);

    /**
     * 查询收藏
     *
     * @param dto 收藏
     * @return 结果
     */
    FavoriteVo getOne(FavoriteDto dto);

    /**
     * 导出收藏
     *
     * @param entity   收藏
     * @param response 响应对象
     */
    void exportExcel(Favorite entity, HttpServletResponse response);

    /**
     * 收藏或取消收藏
     *
     * @param favorite 收藏
     * @return 结果
     */
    Long handleFavorite(Favorite favorite);
}
