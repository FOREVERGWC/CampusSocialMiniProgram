package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CountLikeDto;
import org.example.springboot.system.domain.entity.CountLike;
import org.example.springboot.system.domain.vo.CountLikeVo;
import org.example.springboot.system.service.ICountLikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 点赞量前端控制器
 * </p>
 */
@RestController
@RequestMapping("/countLike")
@Tag(name = "点赞量", description = "点赞量")
public class CountLikeController {
    @Resource
    private ICountLikeService countLikeService;

    /**
     * 添加、修改点赞量
     *
     * @param countLike 点赞量
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改点赞量", description = "添加、修改点赞量", method = "POST")
    public Result<Void> save(@RequestBody CountLike countLike) {
        countLikeService.saveOrUpdate(countLike);
        return Result.success();
    }

    /**
     * 删除点赞量
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除点赞量", description = "删除点赞量", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        countLikeService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询点赞量列表
     *
     * @param dto 点赞量
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询点赞量列表", description = "查询点赞量列表", method = "GET")
    public Result<List<CountLikeVo>> getList(CountLikeDto dto) {
        List<CountLikeVo> list = countLikeService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询点赞量分页
     *
     * @param dto 点赞量
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询点赞量分页", description = "查询点赞量分页", method = "GET")
    public Result<IPage<CountLikeVo>> getPage(CountLikeDto dto) {
        IPage<CountLikeVo> page = countLikeService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询点赞量
     *
     * @param dto 点赞量
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询点赞量", description = "查询点赞量", method = "GET")
    public Result<CountLikeVo> getOne(CountLikeDto dto) {
        CountLikeVo vo = countLikeService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询点赞量
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询点赞量", description = "查询点赞量", method = "GET")
    public Result<CountLikeVo> getById(@PathVariable Long id) {
        CountLikeVo vo = countLikeService.getOne(CountLikeDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出点赞量
     *
     * @param entity   点赞量
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出点赞量", description = "导出点赞量", method = "GET")
    public void exportExcel(CountLike entity, HttpServletResponse response) {
        countLikeService.exportExcel(entity, response);
    }
}
