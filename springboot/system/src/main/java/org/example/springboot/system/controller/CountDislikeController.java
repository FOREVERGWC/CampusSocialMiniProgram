package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CountDislikeDto;
import org.example.springboot.system.domain.entity.CountDislike;
import org.example.springboot.system.domain.vo.CountDislikeVo;
import org.example.springboot.system.service.ICountDislikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 点踩量前端控制器
 * </p>
 */
@RestController
@RequestMapping("/countDislike")
@Tag(name = "点踩量", description = "点踩量")
public class CountDislikeController {
    @Resource
    private ICountDislikeService countDislikeService;

    /**
     * 添加、修改点踩量
     *
     * @param countDislike 点踩量
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改点踩量", description = "添加、修改点踩量", method = "POST")
    public Result<Void> save(@RequestBody CountDislike countDislike) {
        countDislikeService.saveOrUpdate(countDislike);
        return Result.success();
    }

    /**
     * 删除点踩量
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除点踩量", description = "删除点踩量", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        countDislikeService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询点踩量列表
     *
     * @param dto 点踩量
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询点踩量列表", description = "查询点踩量列表", method = "GET")
    public Result<List<CountDislikeVo>> getList(CountDislikeDto dto) {
        List<CountDislikeVo> list = countDislikeService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询点踩量分页
     *
     * @param dto 点踩量
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询点踩量分页", description = "查询点踩量分页", method = "GET")
    public Result<IPage<CountDislikeVo>> getPage(CountDislikeDto dto) {
        IPage<CountDislikeVo> page = countDislikeService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询点踩量
     *
     * @param dto 点踩量
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询点踩量", description = "查询点踩量", method = "GET")
    public Result<CountDislikeVo> getOne(CountDislikeDto dto) {
        CountDislikeVo vo = countDislikeService.getOne(dto);
        return Result.success(vo);
    }
}
