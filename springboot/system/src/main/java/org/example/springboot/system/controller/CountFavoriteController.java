package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CountFavoriteDto;
import org.example.springboot.system.domain.entity.CountFavorite;
import org.example.springboot.system.domain.vo.CountFavoriteVo;
import org.example.springboot.system.service.ICountFavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收藏量前端控制器
 * </p>
 */
@RestController
@RequestMapping("/countFavorite")
@Tag(name = "收藏量", description = "收藏量")
public class CountFavoriteController {
    @Resource
    private ICountFavoriteService countFavoriteService;

    /**
     * 添加、修改收藏量
     *
     * @param countFavorite 收藏量
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改收藏量", description = "添加、修改收藏量", method = "POST")
    public Result<Void> save(@RequestBody CountFavorite countFavorite) {
        countFavoriteService.saveOrUpdate(countFavorite);
        return Result.success();
    }

    /**
     * 删除收藏量
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除收藏量", description = "删除收藏量", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        countFavoriteService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询收藏量列表
     *
     * @param dto 收藏量
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询收藏量列表", description = "查询收藏量列表", method = "GET")
    public Result<List<CountFavoriteVo>> getList(CountFavoriteDto dto) {
        List<CountFavoriteVo> list = countFavoriteService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询收藏量分页
     *
     * @param dto 收藏量
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询收藏量分页", description = "查询收藏量分页", method = "GET")
    public Result<IPage<CountFavoriteVo>> getPage(CountFavoriteDto dto) {
        IPage<CountFavoriteVo> page = countFavoriteService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询收藏量
     *
     * @param dto 收藏量
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询收藏量", description = "查询收藏量", method = "GET")
    public Result<CountFavoriteVo> getOne(CountFavoriteDto dto) {
        CountFavoriteVo vo = countFavoriteService.getOne(dto);
        return Result.success(vo);
    }
}
