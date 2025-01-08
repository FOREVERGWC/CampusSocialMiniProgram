package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.FavoriteDto;
import org.example.springboot.system.domain.entity.Favorite;
import org.example.springboot.system.domain.vo.FavoriteVo;
import org.example.springboot.system.service.IFavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收藏前端控制器
 * </p>
 */
@RestController
@RequestMapping("/favorite")
@Tag(name = "收藏", description = "收藏")
public class FavoriteController {
    @Resource
    private IFavoriteService favoriteService;

    /**
     * 添加、修改收藏
     *
     * @param favorite 收藏
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改收藏", description = "添加、修改收藏", method = "POST")
    public Result<Void> save(@RequestBody Favorite favorite) {
        favoriteService.saveOrUpdate(favorite);
        return Result.success();
    }

    /**
     * 删除收藏
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除收藏", description = "删除收藏", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        favoriteService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询收藏列表
     *
     * @param dto 收藏
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询收藏列表", description = "查询收藏列表", method = "GET")
    public Result<List<FavoriteVo>> getList(FavoriteDto dto) {
        List<FavoriteVo> list = favoriteService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询收藏分页
     *
     * @param dto 收藏
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询收藏分页", description = "查询收藏分页", method = "GET")
    public Result<IPage<FavoriteVo>> getPage(FavoriteDto dto) {
        IPage<FavoriteVo> page = favoriteService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询收藏
     *
     * @param dto 收藏
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询收藏", description = "查询收藏", method = "GET")
    public Result<FavoriteVo> getOne(FavoriteDto dto) {
        FavoriteVo vo = favoriteService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询收藏
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询收藏", description = "查询收藏", method = "GET")
    public Result<FavoriteVo> getById(@PathVariable Long id) {
        FavoriteVo vo = favoriteService.getOne(FavoriteDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出收藏
     *
     * @param entity   收藏
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出收藏", description = "导出收藏", method = "GET")
    public void exportExcel(Favorite entity, HttpServletResponse response) {
        favoriteService.exportExcel(entity, response);
    }
}
